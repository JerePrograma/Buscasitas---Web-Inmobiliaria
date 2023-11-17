package com.proyectofinal.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class FechaHoraContainer {

    private List<LocalDate> fechas;
    private List<LocalTime> horas;

    public FechaHoraContainer() {

    }
}
