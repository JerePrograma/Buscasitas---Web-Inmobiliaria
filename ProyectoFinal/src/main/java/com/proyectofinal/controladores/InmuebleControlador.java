package com.proyectofinal.controladores;

import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.servicios.InmuebleServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/inmueble")
public class InmuebleControlador {

    @Autowired
    private InmuebleServicio inmuebleServicio;

    @GetMapping("")
    public String index(Model model) {

        return "index";
    }

    @GetMapping("/registrar")
    public String registrarInmueble() {
        return "inmueble_form.html";
    }

    @PostMapping("/crear")
    public String crearInmueble(@RequestParam("archivo") MultipartFile archivo,
                                @RequestParam("cuentaTributaria") String cuentaTributaria,
                                @RequestParam("direccion") String direccion,
                                @RequestParam("ciudad") String ciudad,
                                @RequestParam("provincia") String provincia,
                                @RequestParam("transaccion") String transaccion,
                                @RequestParam("listaOfertas") String listaOfertas,
                                @RequestParam("citaDiaHora") String citaDiaHora,
                                @RequestParam("tipoInmueble") String tipoInmueble,
                                @RequestParam("tituloAnuncio") String tituloAnuncio,
                                @RequestParam("descripcionAnuncio") String descripcionAnuncio,
                                @RequestParam("precioAlquilerVenta") Integer precioAlquilerVenta,
                                @RequestParam("caracteristicaInmueble") String caracteristicaInmueble,
                                @RequestParam("estado") String estado,
                                ModelMap modelo) {
        try {
            List<String> listaOfertasList = Arrays.asList(listaOfertas.split(","));
            List<String> citaDiaHoraList = Arrays.asList(citaDiaHora.split(","));

            //Acomodar atributos 
            inmuebleServicio.registrarInmueble(archivo, cuentaTributaria, direccion, ciudad, provincia, transaccion, listaOfertasList, citaDiaHoraList, tipoInmueble, tituloAnuncio, descripcionAnuncio, precioAlquilerVenta, caracteristicaInmueble, estado);
            modelo.put("exito", "El inmueble fue cargado correctamente!");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "inmueble_form.html";
        }
        return "redirect:/";
    }

    @GetMapping("/editar/{cuentaTributaria}")
    public String mostrarFormularioEditar(@PathVariable String cuentaTributaria, Model model) {
        Inmueble inmueble = inmuebleServicio.obtenerInmueblePorCuentaTributaria(cuentaTributaria);
        model.addAttribute("inmueble", inmueble);
        return "inmueble_modificar";
    }

    //    @PostMapping("/editar/{cuentaTributaria}")
//    public String actualizarInmueble(@PathVariable String cuentaTributaria, @ModelAttribute Inmueble inmueble) {
//        inmueble.setCuentaTributaria(cuentaTributaria); // Asegúrate de que la cuenta tributaria no cambie
//        inmuebleServicio.modificarInmueble(inmueble);
//        return "redirect:/inmueble/";
//    }
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
            @RequestParam(name = "transaccion", required = false) String transaccion,
            @RequestParam(name = "tipoInmueble", required = false) String tipoInmueble,
            @RequestParam(name = "ciudad", required = false) String ciudad,
            @RequestParam(name = "provincia", required = false) String provincia,
            Model model
    ) {
        // Llama al servicio con los parámetros adecuados, incluyendo ubicación como String.
        List<Inmueble> inmuebles = inmuebleServicio.buscarInmueblesPorFiltros(ubicacion, transaccion, tipoInmueble, ciudad, provincia);

        // Agrega los resultados al modelo.
        model.addAttribute("inmuebles", inmuebles);

        return "busqueda_inmuebles";
    }
}

