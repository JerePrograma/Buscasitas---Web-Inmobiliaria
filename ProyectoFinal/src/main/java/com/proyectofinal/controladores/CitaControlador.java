package com.proyectofinal.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.entidades.RangoHorario;
import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.servicios.CitaServicio;
import com.proyectofinal.servicios.InmuebleServicio;
import com.proyectofinal.servicios.RangoHorarioServicio;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cita")
public class CitaControlador {

    @Autowired
    private CitaServicio citaServicio;

    @Autowired
    private RangoHorarioServicio rangoHorarioServicio;

    @Autowired
    private InmuebleServicio inmuebleServicio;

    @GetMapping("/registrar/{cuentaTributaria}")
    public String registrarCita(@PathVariable("cuentaTributaria") String cuentaTributaria,
            ModelMap model, HttpSession session) throws Exception {
        Inmueble inmueble = inmuebleServicio.obtenerInmueblePorCuentaTributaria(cuentaTributaria);
        List<RangoHorario> rangoHorario = rangoHorarioServicio.obtenerRangoHorarioPorCuentaTributaria(cuentaTributaria);
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");//enviar la session del usuario logueado
        model.put("inmueble", inmueble);
        Map<Long, List<LocalTime>> horariosDisponiblesMap = new HashMap<>();
        for (RangoHorario r : rangoHorario) {
            List<LocalTime> horariosDisponibles = citaServicio.obtenerHorariosDisponibles(r.getHoraInicio(), r.getHoraFin());
            horariosDisponiblesMap.put(r.getId(), horariosDisponibles);
        }

        model.put("horariosDisponiblesMap", horariosDisponiblesMap);
        model.put("rangoHorario", rangoHorario);
        model.put("usuario", usuario);
        return "cita_form.html";
    }

    @PostMapping("/registrar/{cuentaTributaria}")
    public String registrarCita(@RequestParam String idEnte, @RequestParam String idCliente, @RequestParam Long idHorario,
            @RequestParam(required = false) String nota, ModelMap modelo) {
        try {
            System.out.println( "Id del ente: " + idEnte);
            System.out.println("Id del CLiente: " + idCliente);
            citaServicio.crearCita(idEnte, idCliente, idHorario, nota);
            modelo.put("exito", "la cita fue cargada correctamente");
        } catch (Exception e) {
            System.out.println(e);
            return "cita_form";
        }

        return "redirect:/";
    }

}
