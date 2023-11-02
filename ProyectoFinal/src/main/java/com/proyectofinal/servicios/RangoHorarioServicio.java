package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.entidades.RangoHorario;
import com.proyectofinal.repositorios.RangoHorarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Transactional
    public RangoHorario actualizarRangoHorario(RangoHorario rangoHorario, LocalTime horaInicio, LocalTime horaFin) {
        if (rangoHorario != null) {
            rangoHorario.setHoraInicio(horaInicio);
            rangoHorario.setHoraFin(horaFin);
            return rangoHorarioRepositorio.save(rangoHorario);
        }
        throw new IllegalArgumentException("El rango horario proporcionado es nulo.");
    }

    @Transactional
    public void eliminarRangoHorario(Long id) throws Exception {
        Optional<RangoHorario> rangoHorarioOptional = rangoHorarioRepositorio.findById(id);
        if (rangoHorarioOptional.isPresent()) {
            rangoHorarioRepositorio.delete(rangoHorarioOptional.get());
        } else {
            throw new Exception("No se encontró el rango horario con ID: " + id);
        }
    }

    public RangoHorario obtenerRangoHorarioPorId(Long id) throws Exception {
        Optional<RangoHorario> rangoHorarioOptional = rangoHorarioRepositorio.findById(id);
        if (rangoHorarioOptional.isPresent()) {
            return rangoHorarioOptional.get();
        }
        throw new Exception("No se encontró el rango horario con ID: " + id);
    }

    public List<RangoHorario> obtenerTodosLosRangosHorarios() {
        return rangoHorarioRepositorio.findAll();
    }
}
