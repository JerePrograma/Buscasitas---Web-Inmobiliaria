/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Cita;
import com.proyectofinal.entidades.RangoHorario;
import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.repositorios.CitaRepositorio;
import com.proyectofinal.repositorios.RangoHorarioRepositorio;
import com.proyectofinal.repositorios.UsuarioRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author matob
 */
@Service
public class CitaServicio {

    @Autowired
    CitaRepositorio citaRepositorio;
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    RangoHorarioRepositorio rangoHorarioRepositorio;

    @Transactional
    public void crearCita(String idEnte, String idCliente, String idHorario, String nota) {
        ///  Validar(Usuario ente,Usuario cliente, RangoHorario horario);

        Usuario ente = usuarioRepositorio.buscarPorEmail(idEnte);
        Usuario cliente = usuarioRepositorio.buscarPorEmail(idCliente);
        RangoHorario horario = rangoHorarioRepositorio.findById(idHorario);

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
        Optional<RangoHorario> respuestaHorario = Optional.ofNullable(rangoHorarioRepositorio.findById(idHorario));

        Usuario ente = new Usuario();
        Usuario cliente = new Usuario();
        RangoHorario horario = new RangoHorario();

        if (respuestaEnte.isPresent()) {
            ente = respuestaEnte.get();
        }
        if (respuestaCliente.isPresent()) {
            cliente = respuestaCliente.get();
        }
        if (respuestaHorario.isPresent()) {
            horario = respuestaHorario.get();
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

//    private void Validar(Usuario ente,Usuario cliente, RangoHorario horario){
//        
//         if (ente == null) {
//            throw new MiExeption("el ente no puede ser nulo");
//        }
//        if (cliente == null) {
//            throw new MiExeption("el cliente no puede ser nulo");
//        }
//        if (horario == null) {
//            throw new MiExeption("el horario no puede ser nulo");
//        }
//    }
}
