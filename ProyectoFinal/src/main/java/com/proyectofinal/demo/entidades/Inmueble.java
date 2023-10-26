
package com.proyectofinal.demo.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import com.proyectofinal.demo.enumeraciones.TipoInmueble;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List <String> listaOfertas;
    @Column
    @ElementCollection
    private List<String> citaDiaHora;

    @Enumerated(EnumType.STRING)
    private TipoInmueble tipoInmueble;

    @OneToOne
    private Imagen imagen;
    
}
