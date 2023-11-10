package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Cita;
import com.proyectofinal.entidades.RangoHorario;
import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.repositorios.CitaRepositorio;
import com.proyectofinal.repositorios.RangoHorarioRepositorio;
import com.proyectofinal.repositorios.UsuarioRepositorio;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaServicio {

    @Autowired
    CitaRepositorio citaRepositorio;
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    RangoHorarioRepositorio rangoHorarioRepositorio;

    @Transactional
    public void registrarCita(String idEnte, String idCliente, String idHorario, String nota) {
        ///  Validar(Usuario ente,Usuario cliente, RangoHorario horario);

        Usuario ente = usuarioRepositorio.buscarPorEmail(idEnte);
        Usuario cliente = usuarioRepositorio.buscarPorEmail(idCliente);
        List<RangoHorario> horario = (List<RangoHorario>) rangoHorarioRepositorio.findById(idHorario);

        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setEnte(ente);
        cita.setHorario(horario);
        cita.setNota(nota);
        citaRepositorio.save(cita);
    }

    @Transactional
    public void modificarCita(String id, String idEnte, String idCliente, String idHorario, String nota) {
        ///  Validar(Usuario ente,Usuario cliente, RangoHorario horario);

        Optional<Cita> respuesta = citaRepositorio.findById(id);
        Optional<Usuario> respuestaEnte = usuarioRepositorio.findById(idEnte);
        Optional<Usuario> respuestaCliente = usuarioRepositorio.findById(idCliente);
        Optional<RangoHorario> respuestaHorario = Optional.ofNullable((RangoHorario) rangoHorarioRepositorio.findById(idHorario));

        Usuario ente = new Usuario();
        Usuario cliente = new Usuario();
        List<RangoHorario> horario = (List<RangoHorario>) new RangoHorario();

        if (respuestaEnte.isPresent()) {
            ente = respuestaEnte.get();
        }
        if (respuestaCliente.isPresent()) {
            cliente = respuestaCliente.get();
        }
        if (respuestaHorario.isPresent()) {
            horario = Collections.singletonList(respuestaHorario.get());
        }
        if (respuesta.isPresent()) {

            Cita cita = respuesta.get();

            cita.setCliente(cliente);
            cita.setEnte(ente);
            cita.setHorario(horario);
            cita.setNota(nota);

            citaRepositorio.save(cita);
        }
    }

    public List<LocalTime> obtenerHorariosDisponibles(LocalTime inicio, LocalTime fin) {
        List<LocalTime> horarios = new ArrayList<>();
        while (inicio.isBefore(fin) || inicio.equals(fin)) {
            horarios.add(inicio);
            inicio = inicio.plusMinutes(30); // Incrementa en intervalos de 30 minutos
        }
        return horarios;
    }
}
