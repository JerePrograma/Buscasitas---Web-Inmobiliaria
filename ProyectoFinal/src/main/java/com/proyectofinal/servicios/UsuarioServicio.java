package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public List<Usuario> listarUsuarios() {

        return null;

    }

}
