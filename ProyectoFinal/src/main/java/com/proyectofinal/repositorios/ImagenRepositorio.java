package com.proyectofinal.repositorios;

import com.proyectofinal.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenRepositorio extends JpaRepository <Imagen, String> {

}
