package com.proyectofinal.controladores;

import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.entidades.RangoHorario;
import com.proyectofinal.repositorios.RangoHorarioRepositorio;
import com.proyectofinal.servicios.InmuebleServicio;
import com.proyectofinal.servicios.RangoHorarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/inmueble")
public class InmuebleControlador {

    @Autowired
    private InmuebleServicio inmuebleServicio;

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
            // Crea una instancia de Inmueble
            Inmueble inmueble = new Inmueble();
// Configura los atributos de Inmueble según sea necesario
            inmueble.setCuentaTributaria(cuentaTributaria);
            inmueble.setDireccion(direccion);
            inmueble.setCiudad(ciudad);
            inmueble.setProvincia(provincia);
            inmueble.setTransaccion(transaccion);
            inmueble.setTipoInmueble(tipoInmueble);
            inmueble.setDescripcionAnuncio(descripcionAnuncio);
            inmueble.setPrecioAlquilerVenta(precioAlquilerVenta);
            inmueble.setCaracteristicaInmueble(caracteristicaInmueble);
            inmueble.setEstado(estado);
            inmueble.setTituloAnuncio(tituloAnuncio);
            inmueble.setAlta(true);

// Crea una lista para los RangoHorario

            List<RangoHorario> rangosHorarios = new ArrayList<>();

            for (int i = 0; i < diaSemanaList.size(); i++) {
                LocalTime horaInicio = LocalTime.parse(horaInicioList.get(i));
                LocalTime horaFin = LocalTime.parse(horaFinList.get(i));

                RangoHorario rangoHorario = rangoHorarioServicio.crearRangoHorario(diaSemanaList.get(i), horaInicio, horaFin, inmueble);

                // Establece la relación con la instancia de Inmueble que ya ha sido guardada
                rangoHorario.setInmueble(inmueble);

                System.out.println("Asignando rangoHorario a Inmueble: " + rangoHorario);

                rangosHorarios.add(rangoHorario);
            }

// Asigna la lista de rangosHorarios a la instancia de Inmueble
            inmueble.setRangosHorarios(rangosHorarios);

// Llama al servicio para registrar el Inmueble con sus RangoHorario

            modelo.put("exito", "El inmueble fue cargado correctamente!");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            System.out.println(ex.getMessage());
            return "inmueble_form.html";
        }
        return "redirect:/";
    }

    @GetMapping("/lista")
    public String listarInmueble(ModelMap modelo) {
        List<Inmueble> inmuebles = inmuebleServicio.listarTodosLosInmuebles();

        modelo.addAttribute("inmuebles", inmuebles);

        return "inmueble_lista";
    }

    @GetMapping("/modificar/{cuentaTributaria}")
    public String editarInmueble(@PathVariable String cuentaTributaria, ModelMap model) {
        Inmueble inmueble = inmuebleServicio.obtenerInmueblePorCuentaTributaria(cuentaTributaria);
        model.put("inmueble", inmueble);
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
                                     ModelMap model) {

        try {

            System.out.println(cuentaTributaria);
          //Inmueble inmueble = inmuebleServicio.obtenerInmueblePorCuentaTributaria(cuentaTributaria);
            inmuebleServicio.modificarInmueble(archivo, cuentaTributaria, tituloAnuncio, descripcionAnuncio, caracteristicaInmueble, estado);

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

    @GetMapping("/eliminar/{cuentaTributaria}")
    public String eliminarInmueble(@PathVariable String cuentaTributaria) {
        inmuebleServicio.eliminarInmueblePorCuentaTributaria(cuentaTributaria);
        return "redirect:/inmueble/";
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
        List<Inmueble> inmuebles = inmuebleServicio.buscarInmueblesPorFiltros(ubicacion, transaccion, tipoInmueble, ciudad, provincia);

        // Agrega los resultados al modelo.
        model.addAttribute("inmuebles", inmuebles);

        return "busqueda_inmuebles";
    }

    @GetMapping("/buscar-inmuebles")
    public String buscarUbicacionInmuebles(
            @RequestParam(name = "ubicacion", required = false) String ubicacion,
            Model model
    ) {
        // Llama al servicio con los parámetros adecuados, incluyendo ubicación como String.
        List<Inmueble> inmuebles = inmuebleServicio.buscarPorUbicacion(ubicacion);

        // Agrega los resultados al modelo.
        model.addAttribute("inmuebles", inmuebles);

        return "busqueda_inmuebles";
    }
}
