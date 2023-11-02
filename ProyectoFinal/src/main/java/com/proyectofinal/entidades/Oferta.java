package com.proyectofinal.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
public class Oferta implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idOferta;
    private Integer valorOferta;
    private LocalDate fechaOferta;
    private LocalDate fechaAceptacion;
    private LocalDate fechaRevocacion;
    private String estadoOferta;

    @ManyToOne
    private Inmueble inmueble; // varias ofertas a un inmueble?

    @ManyToOne
    private Usuario usuario; // varias ofertas a un usuario?

}
