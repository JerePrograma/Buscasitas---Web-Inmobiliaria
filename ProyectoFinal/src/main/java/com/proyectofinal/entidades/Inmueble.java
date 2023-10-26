package com.proyectofinal.entidades;

import com.proyectofinal.enumeraciones.TipoInmueble;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inmueble implements Serializable {

    @Id
    private String cuentaTributaria;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String transaccion; // input compra, venta, alquiler anual, alquiler temporario
    @Column
    @ElementCollection
    private List<String> listaOfertas;
    @Column
    @ElementCollection
    private List<String> citaDiaHora;

    @Enumerated(EnumType.STRING)
    private TipoInmueble tipoInmueble;

    @OneToOne
    private Imagen imagen;

}
