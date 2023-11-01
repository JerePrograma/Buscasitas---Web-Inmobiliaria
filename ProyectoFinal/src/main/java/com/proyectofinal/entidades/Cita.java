package com.proyectofinal.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author matob
 */
@Entity
@Data
@NoArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne
    private Usuario ente;
    @ManyToOne
    private Usuario cliente;

    @OneToOne
    private RangoHorario horario;

    private String Nota;


}