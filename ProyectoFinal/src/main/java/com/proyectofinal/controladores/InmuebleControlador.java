package com.proyectofinal.controladores;

import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.entidades.RangoHorario;
import com.proyectofinal.servicios.ImagenServicio;
import com.proyectofinal.servicios.InmuebleServicio;
import com.proyectofinal.servicios.RangoHorarioServicio;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
            @RequestParam("estado") String estado,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("cuentaTributaria") String cuentaTributaria,
            @RequestParam("direccion") String direccion,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("provincia") String provincia,
            @RequestParam("transaccion") String transaccion,
            @RequestParam("tipoInmueble") String tipoInmueble,
            @RequestParam("tituloAnuncio") String tituloAnuncio,
            @RequestParam("descripcionAnuncio") String descripcionAnuncio,
            @RequestParam("precioAlquilerVenta") Integer precioAlquilerVenta,
            @RequestParam("caracteristicaInmueble") String caracteristicaInmueble,
            @RequestParam("diaSemana") List<String> diaSemanaList,
            @RequestParam("horaInicio") List<String> horaInicioList, // Cambiado a List<String>
            @RequestParam("horaFin") List<String> horaFinList) { // Cambiado a List<String>
        try {

            // Llama al servicio para registrar el Inmueble con sus RangoHorario
            inmuebleServicio.registrarInmueble(archivo, cuentaTributaria, direccion, ciudad, provincia, transaccion,
                    tipoInmueble, tituloAnuncio, descripcionAnuncio, precioAlquilerVenta, caracteristicaInmueble,
                    estado);
            Inmueble inmueble = inmuebleServicio.obtenerInmueblePorCuentaTributaria(cuentaTributaria);
            rangoHorarioServicio.establecerRangoHorarios(diaSemanaList, horaInicioList, horaFinList, inmueble);

            modelo.put("exito", "El inmueble fue cargado correctamente!");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
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
        RangoHorario rangoHorario = rangoHorarioServicio.obtenerRangoHorarioPorCuentaTributaria(cuentaTributaria);
        model.put("inmueble", inmueble);
        model.put("rangoHorario", rangoHorario);
        model.addAttribute("cuentaTributaria", cuentaTributaria);
        return "inmueble_modificar"; // Crea una página HTML para la edición del inmueble
    }

    @PostMapping("/modificar/{cuentaTributaria}")
    public String actualizarInmueble(@PathVariable("cuentaTributaria") String cuentaTributaria,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("tituloAnuncio") String tituloAnuncio,
            @RequestParam("descripcionAnuncio") String descripcionAnuncio,
            @RequestParam("caracteristicaInmueble") String caracteristicaInmueble,
            @RequestParam("estado") String estado,
            @RequestParam("diaSemana") List<String> diaSemanaList,
            @RequestParam("horaInicio") List<String> horaInicioList,
            @RequestParam("horaFin") List<String> horaFinList,
            ModelMap model) {

        try {
            System.out.println(cuentaTributaria);
            //Modificar inmueble
            Inmueble inmueble = inmuebleServicio.obtenerInmueblePorCuentaTributaria(cuentaTributaria);
            inmuebleServicio.modificarInmueble(archivo, cuentaTributaria, tituloAnuncio, descripcionAnuncio, caracteristicaInmueble, estado);
            //Modificar RangoHorario
            RangoHorario rangoHorario = rangoHorarioServicio.obtenerRangoHorarioPorCuentaTributaria(cuentaTributaria);
            rangoHorarioServicio.actualizarRangoHorario(rangoHorario, diaSemanaList, horaInicioList, horaFinList);

            // Resto del código
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
        if ((ubicacion == null || ubicacion.isEmpty()) &&
                (transaccion == null || transaccion.isEmpty()) &&
                (tipoInmueble == null || tipoInmueble.isEmpty()) &&
                (ciudad == null || ciudad.isEmpty()) &&
                (provincia == null || provincia.isEmpty())) {
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
        RangoHorario rangoHorario = rangoHorarioServicio.obtenerRangoHorarioPorCuentaTributaria(cuentaTributaria);

        if (inmueble != null) {
            // Realiza la conversión de la imagen a base64
            byte[] imagenContenido = inmueble.getImagen().getContenido();
            String imagenBase64 = Base64.getEncoder().encodeToString(imagenContenido);

//             Agrega la imagen base64 al modelo
            model.addAttribute("imagenBase64", imagenBase64);
            // Agrega el inmueble al modelo
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
