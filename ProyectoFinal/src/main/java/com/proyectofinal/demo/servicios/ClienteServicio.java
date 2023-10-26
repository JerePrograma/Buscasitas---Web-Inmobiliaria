package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.proyectofinal.repositorios.ClienteRepositorio;

public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<Usuario> listarUsuarios() {

        return null;

    }
}