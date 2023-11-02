package com.proyectofinal.entidades;

import com.proyectofinal.entidades.Inmueble;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class RangoHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diaSemana; // Día de la semana (por ejemplo, "Lunes", "Martes", etc.)

    @Column(name = "hora_inicio")
    private LocalTime horaInicio; // Hora de inicio del rango horario

    @Column(name = "hora_fin")
    private LocalTime horaFin; // Hora de fin del rango horario

    @ManyToOne
    @JoinColumn(name = "cuenta_tributaria", referencedColumnName = "cuentaTributaria")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Inmueble inmueble;



    // Otras propiedades y getters/setters
}
