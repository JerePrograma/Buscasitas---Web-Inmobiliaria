package com.proyectofinal.entidades;

import com.proyectofinal.enumeraciones.Estado;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Inmueble implements Serializable {

    @Id
    private String cuentaTributaria;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String transaccion; // input compra, venta, alquiler anual, alquiler temporario 
    private List<String> listaOfertas;
    private List<String> citaDiaHora;
    private Usuario propietario;
    private String tituloAnuncio;
    private String descripcionPropiedad;
    private Integer precioAlquilerVenta;
    private String caracteristicasInmueble;

    @Enumerated
    private Estado estado;

    @Enumerated
    private String TipoInmueble;
}
