package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.entidades.RangoHorario;
import com.proyectofinal.repositorios.RangoHorarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RangoHorarioServicio {

    @Autowired
    private RangoHorarioRepositorio rangoHorarioRepositorio;

    @Transactional
    public RangoHorario crearRangoHorario(String diaSemana, LocalTime horaInicio, LocalTime horaFin, Inmueble inmueble) {
        RangoHorario rangoHorario = new RangoHorario();
        rangoHorario.setDiaSemana(diaSemana);
        rangoHorario.setHoraInicio(horaInicio);
        rangoHorario.setHoraFin(horaFin);
        rangoHorario.setInmueble(inmueble);
        return rangoHorarioRepositorio.save(rangoHorario);
    }

    public List<RangoHorario> establecerRangoHorarios(List<String> diaSemanaList, List<String> horaInicioList, List<String> horaFinList, Inmueble inmueble) {
        List<RangoHorario> rangosHorarios = new ArrayList<>();
        for (int i = 0; i < diaSemanaList.size(); i++) {
            LocalTime horaInicio = LocalTime.parse(horaInicioList.get(i));
            LocalTime horaFin = LocalTime.parse(horaFinList.get(i));

            RangoHorario rangoHorario = crearRangoHorario(diaSemanaList.get(i), horaInicio, horaFin, inmueble);

            // Establece la relación con la instancia de Inmueble que ya ha sido guardada
            rangoHorario.setInmueble(inmueble);

            System.out.println("Asignando rangoHorario a Inmueble: " + rangoHorario);

            rangosHorarios.add(rangoHorario);
        }
        return rangosHorarios;
    }

    @Transactional
    public RangoHorario actualizarRangoHorario(RangoHorario rangoHorario, List<String> diaSemanaList, List<String> horaInicioList, List<String> horaFinList) {
        if (rangoHorario != null) {
            List<RangoHorario> rangosHorarios = new ArrayList<>();
            for (int i = 0; i < diaSemanaList.size(); i++) {
                LocalTime horaInicio = LocalTime.parse(horaInicioList.get(i));
                LocalTime horaFin = LocalTime.parse(horaFinList.get(i));
                rangoHorario.setDiaSemana(String.valueOf(diaSemanaList));
                rangoHorario.setHoraInicio(horaInicio);
                rangoHorario.setHoraFin(horaFin);
                return rangoHorarioRepositorio.save(rangoHorario);
            }
        }
        throw new IllegalArgumentException("El rango horario proporcionado es nulo.");
    }

    @Transactional
    public void eliminarInmueblePorCuentaTributaria(String cuentaTributaria) {
        // Implementa la lógica para eliminar un inmueble por su cuenta tributaria
        List<RangoHorario> rangosHorarios = (List<RangoHorario>) rangoHorarioRepositorio.findByCuentaTributaria(cuentaTributaria);
        rangoHorarioRepositorio.deleteAll(rangosHorarios);
    }

    public RangoHorario obtenerRangoHorarioPorId(Long id) throws Exception {
        Optional<RangoHorario> rangoHorarioOptional = rangoHorarioRepositorio.findById(id);
        if (rangoHorarioOptional.isPresent()) {
            return rangoHorarioOptional.get();
        }
        throw new Exception("No se encontró el rango horario con ID: " + id);
    }

    public List<RangoHorario> obtenerRangoHorarioPorCuentaTributaria(String cuentaTributaria) throws Exception {
        return rangoHorarioRepositorio.findByCuentaTributaria(cuentaTributaria);
    }

    public List<RangoHorario> obtenerTodosLosRangosHorarios() {
        return rangoHorarioRepositorio.findAll();
    }
}
