package com.proyectofinal.repositorios;

import com.proyectofinal.entidades.RangoHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RangoHorarioRepositorio extends JpaRepository<RangoHorario, Long> {
    RangoHorario findById(String idHorario);
}
