package com.proyectofinal.entidades;

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
    private String tituloAnuncio;
    private String descripcionAnuncio;
    private Integer precioAlquilerVenta;
    private String caracteristicaInmueble;
    private Boolean alta;
    private String tipoInmueble;
    private String estado;

    @Column
    @ElementCollection
    private List<String> listaOfertas;

    @Column
    @ElementCollection
    private List<String> citaDiaHora;

    @OneToOne
    private Imagen imagen;

    @ManyToOne
    @JoinColumn(name = "usuario_administrador_id")
    private Usuario usuarioAdministrador;

}
