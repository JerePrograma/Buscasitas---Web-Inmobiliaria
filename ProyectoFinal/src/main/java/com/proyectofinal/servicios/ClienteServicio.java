package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<Usuario> listarUsuarios() {

        return null;

    }
}
