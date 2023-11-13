package com.proyectofinal.controladores;

import com.proyectofinal.entidades.Imagen;
import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.entidades.RangoHorario;
import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.servicios.ImagenServicio;
import com.proyectofinal.servicios.InmuebleServicio;
import com.proyectofinal.servicios.RangoHorarioServicio;
import java.util.ArrayList;

import java.util.Base64;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/inmueble")
public class InmuebleControlador {

    private final InmuebleServicio inmuebleServicio;

    @Autowired
    public InmuebleControlador(@Lazy InmuebleServicio inmuebleServicio) {
        this.inmuebleServicio = inmuebleServicio;
    }

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    RangoHorarioServicio rangoHorarioServicio;

    @GetMapping("/registrar")
    public String registrarInmueble() {
        return "inmueble_form.html";
    }

    @PostMapping("/registrar")
    public String registrarInmueble(ModelMap modelo,
            @RequestParam("archivoPrincipal") MultipartFile archivoPrincipal,
            @RequestParam("archivosSecundarios") MultipartFile[] archivosSecundarios,
            @RequestParam("cuentaTributaria") String cuentaTributaria,
            @RequestParam("direccion") String direccion,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("provincia") String provincia,
            @RequestParam("transaccion") String transaccion,
            @RequestParam("tipoInmueble") String tipoInmueble,
            @RequestParam("tituloAnuncio") String tituloAnuncio,
            @RequestParam("descripcionAnuncio") String descripcionAnuncio,
            @RequestParam("precioAlquilerVenta") Integer precioAlquilerVenta,
            @RequestParam("cantidadHabitaciones") Integer cantidadHabitaciones,
            @RequestParam("banios") Integer banios,
            @RequestParam("cantidadAmbientes") Integer cantidadAmbientes,
            @RequestParam("altura") int altura,
            @RequestParam("largo") int largo,
            @RequestParam("diaSemana") List<String> diaSemanaList,
            @RequestParam("horaInicio") List<String> horaInicioList,
            @RequestParam("horaFin") List<String> horaFinList, HttpSession session) {

        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        try {
            // Guardar el inmueble y capturar la instancia guardada
            Inmueble inmuebleGuardado = inmuebleServicio.registrarInmueble(
                    archivoPrincipal, archivosSecundarios, cuentaTributaria, direccion, ciudad, provincia,
                    transaccion, tipoInmueble, tituloAnuncio, descripcionAnuncio,
                    precioAlquilerVenta, cantidadHabitaciones, banios, cantidadAmbientes,
                    altura, largo, usuario);

            // Establecer los rangos horarios con el inmueble guardado
            rangoHorarioServicio.establecerRangoHorarios(diaSemanaList, horaInicioList, horaFinList, inmuebleGuardado);

            modelo.put("exito", "El inmueble fue cargado correctamente!");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            // Log para el mensaje de la excepción
            System.out.println(ex.getMessage());
            return "inmueble_form.html";
        }
        return "redirect:/";
    }

    @GetMapping("/lista")
    public String listarInmueble(ModelMap modelo) throws Exception {
        List<Inmueble> inmuebles = inmuebleServicio.listarTodosLosInmuebles();
        for (Inmueble inmueble : inmuebles) {
            List<RangoHorario> rangosHorarios = rangoHorarioServicio.obtenerTodosLosRangosHorarios();
            inmueble.setRangosHorarios(rangosHorarios);
        }

        modelo.addAttribute("inmuebles", inmuebles);

        return "inmueble_lista";
    }

    @GetMapping("/modificar/{cuentaTributaria}")
    public String editarInmueble(@PathVariable String cuentaTributaria, ModelMap model) throws Exception {
        Inmueble inmueble = inmuebleServicio.obtenerInmueblePorCuentaTributaria(cuentaTributaria);
        List<RangoHorario> rangoHorario = rangoHorarioServicio.obtenerRangoHorarioPorCuentaTributaria(cuentaTributaria);
        model.put("inmueble", inmueble);
        model.put("rangoHorario", rangoHorario);
        model.addAttribute("cuentaTributaria", cuentaTributaria);
        return "inmueble_modificar"; // Crea una página HTML para la edición del inmueble
    }

    @PostMapping("/modificar/{cuentaTributaria}")
    public String actualizarInmueble(@PathVariable("cuentaTributaria") String cuentaTributaria,
            @RequestParam("imagenPrincipal") MultipartFile archivoPrincipal,
            @RequestParam("archivos") MultipartFile[] archivosSecundarios,
            @RequestParam("tituloAnuncio") String tituloAnuncio,
            @RequestParam("descripcionAnuncio") String descripcionAnuncio,
            @RequestParam("caracteristicaInmueble") String caracteristicaInmueble,
            @RequestParam("estado") String estado,
            @RequestParam("diaSemana") List<String> diaSemanaList,
            @RequestParam("horaInicio") List<String> horaInicioList,
            @RequestParam("horaFin") List<String> horaFinList,
            ModelMap model) {
        try {
            // Modificar inmueble
            inmuebleServicio.modificarInmueble(cuentaTributaria, archivoPrincipal, archivosSecundarios, tituloAnuncio, descripcionAnuncio, caracteristicaInmueble, estado);

            // Modificar RangoHorario
            RangoHorario rangoHorario = (RangoHorario) rangoHorarioServicio.obtenerRangoHorarioPorCuentaTributaria(cuentaTributaria);
            rangoHorarioServicio.actualizarRangoHorario(rangoHorario, diaSemanaList, horaInicioList, horaFinList);

            model.put("exito", "Los cambios fueron guardados correctamente!");
            return "redirect:/"; // Redirige a la página principal o la página de éxito, según sea necesario
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
            System.out.println("error" + ex.getMessage());
            System.out.println(cuentaTributaria);
            return "inmueble_modificar"; // Permanece en la página de edición y muestra el mensaje de error
        }
    }

    @GetMapping("/busqueda")
    public String buscarInmuebles(
            @RequestParam(name = "ubicacion", required = false) String ubicacion,
            @RequestParam(name = "transaccion", required = false) String transaccion,
            @RequestParam(name = "tipoInmueble", required = false) String tipoInmueble,
            @RequestParam(name = "ciudad", required = false) String ciudad,
            @RequestParam(name = "provincia", required = false) String provincia,
            @RequestParam(name = "archivo", required = false) MultipartFile archivo,
            Model model
    ) {
        // Llama al servicio con los parámetros adecuados, incluyendo tipoInmueble como String.
        List<Inmueble> inmuebles;
        if ((ubicacion == null || ubicacion.isEmpty())
                && (transaccion == null || transaccion.isEmpty())
                && (tipoInmueble == null || tipoInmueble.isEmpty())
                && (ciudad == null || ciudad.isEmpty())
                && (provincia == null || provincia.isEmpty())) {

            // No se ingresaron criterios de búsqueda, obtener todos los inmuebles
            inmuebles = inmuebleServicio.listarTodosLosInmuebles();
        } else {
            // Realizar la búsqueda con los criterios ingresados
            inmuebles = inmuebleServicio.buscarInmueblesPorFiltros(
                    (ubicacion != null) ? ubicacion : "",
                    (transaccion != null) ? transaccion : "",
                    (tipoInmueble != null) ? tipoInmueble : "",
                    (ciudad != null) ? ciudad : "",
                    (provincia != null) ? provincia : ""
            );
        }
        // Agrega los resultados al modelo.
        model.addAttribute("inmuebles", inmuebles);

        return "busqueda_inmuebles";
    }

    @GetMapping("/detalle/{cuentaTributaria}")
    public String detalleInmueble(@PathVariable String cuentaTributaria, Model model) throws Exception {
        Inmueble inmueble = inmuebleServicio.obtenerInmueblePorCuentaTributaria(cuentaTributaria);
        List<RangoHorario> rangoHorario = rangoHorarioServicio.obtenerRangoHorarioPorCuentaTributaria(cuentaTributaria);

        if (inmueble != null) {
            List<Imagen> imagenes = inmueble.getImagenesSecundarias();
            List<Map<String, String>> imagenesInfo = new ArrayList<>();

            for (Imagen imagen : imagenes) {
                Map<String, String> imageData = new HashMap<>();
                byte[] imagenContenido = imagen.getContenido();
                String imagenBase64 = Base64.getEncoder().encodeToString(imagenContenido);
                imageData.put("base64", imagenBase64);
                imageData.put("mime", imagen.getMime());
                imagenesInfo.add(imageData);
            }

            // Agrega la lista de imágenes en base64 y su mime al modelo
            model.addAttribute("imagenesInfo", imagenesInfo);

            // Agrega el inmueble y el rango horario al modelo
            model.addAttribute("inmueble", inmueble);
            model.addAttribute("rangoHorario", rangoHorario);

            return "inmueble_detalle";
        } else {
            // Manejar el caso en el que no se encuentra el inmueble
            return "error"; // Puedes crear una vista específica para errores.
        }
    }

    @GetMapping("/eliminar/{cuentaTributaria}")
    public String eliminarInmueble(@PathVariable String cuentaTributaria) throws Exception {
        inmuebleServicio.eliminarInmueblePorCuentaTributaria(cuentaTributaria);
        rangoHorarioServicio.eliminarInmueblePorCuentaTributaria(cuentaTributaria);
        return "redirect:/";
    }

    @GetMapping("/dar-baja/{cuentaTributaria}")
    public String darBajaInmueble(@PathVariable("cuentaTributaria") String cuentaTributaria,
            ModelMap modelo) throws Exception {
        inmuebleServicio.darBajaInmueble(cuentaTributaria);

        return "index.html";
    }

    @GetMapping("/dar-alta/{cuentaTributaria}")
    public String darAltaInmueble(@PathVariable("cuentaTributaria") String cuentaTributaria,
            ModelMap modelo) throws Exception {
        inmuebleServicio.darAltaInmueble(cuentaTributaria);

        return "index.html";
    }
}
