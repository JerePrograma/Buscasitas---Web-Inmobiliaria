package com.proyectofinal.controladores;

import com.proyectofinal.entidades.FechaHoraContainer;
import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.entidades.RangoHorario;
import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.servicios.CitaServicio;
import com.proyectofinal.servicios.InmuebleServicio;
import com.proyectofinal.servicios.RangoHorarioServicio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            System.out.println("Id del ente: " + idEnte);
            System.out.println("Id del CLiente: " + idCliente);
            citaServicio.crearCita(idEnte, idCliente, idHorario, nota);
            modelo.put("exito", "la cita fue cargada correctamente");
        } catch (Exception e) {
            System.out.println(e);
            return "cita_form";
        }

        return "redirect:/";
    }

    @GetMapping("/horarios-disponibles/{cuentaTributaria}")
    @ResponseBody
    public ResponseEntity<FechaHoraContainer> obtenerHorariosDisponibles(@PathVariable String cuentaTributaria) {
        try {
            // Obtener el rango horario según la cuenta tributaria
            List<RangoHorario> rangoHorario = rangoHorarioServicio.obtenerRangoHorarioPorCuentaTributaria(cuentaTributaria);

            // Validar si el rangoHorario existe
            if (rangoHorario == null) {
                return ResponseEntity.notFound().build();
            }

            // Obtener todas las fechas disponibles
            List<LocalDate> fechasDisponibles = rangoHorario.stream()
                    .map(RangoHorario::getFecha)
                    .collect(Collectors.toList());

            // Obtener todas las horas disponibles
            List<LocalTime> horasDisponibles = rangoHorario.stream()
                    .flatMap(rango -> {
                        LocalTime horaInicio = rango.getHoraInicio();
                        LocalTime horaFin = rango.getHoraFin();
                        List<LocalTime> horas = new ArrayList<>();
                        while (horaInicio.isBefore(horaFin) || horaInicio.equals(horaFin)) {
                            horas.add(horaInicio);
                            horaInicio = horaInicio.plusMinutes(30); // Puedes ajustar el intervalo según tus necesidades
                        }
                        return horas.stream();
                    })
                    .collect(Collectors.toList());

            // Crear un objeto FechaHoraContainer con ambas propiedades
            FechaHoraContainer fechaHoraContainer = new FechaHoraContainer();
            fechaHoraContainer.setFechas(fechasDisponibles);
            fechaHoraContainer.setHoras(horasDisponibles);

            // Devolver el objeto FechaHoraContainer en la respuesta
            return ResponseEntity.ok(fechaHoraContainer);
        } catch (Exception e) {
            // Manejar cualquier excepción y devolver un código de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
