package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.repositorios.InmuebleRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class InmuebleServicio {

    @Autowired
    private InmuebleRepositorio inmuebleRepositorio;

    public List<Inmueble> listarInmueble() {

        return null;
    }
}