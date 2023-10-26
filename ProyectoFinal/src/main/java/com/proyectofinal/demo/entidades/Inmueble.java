<<<<<<< HEAD:ProyectoFinal/src/main/java/com/proyectofinal/demo/entidades/Inmueble.java

package com.proyectofinal.demo.entidades;
=======
package com.proyectofinal.entidades;
>>>>>>> 1af475c9326bac16537cf1d6fd921d6714f2a417:ProyectoFinal/src/main/java/com/proyectofinal/entidades/Inmueble.java

import com.proyectofinal.enumeraciones.Estado;
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
<<<<<<< HEAD:ProyectoFinal/src/main/java/com/proyectofinal/demo/entidades/Inmueble.java
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
    
=======
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
>>>>>>> 1af475c9326bac16537cf1d6fd921d6714f2a417:ProyectoFinal/src/main/java/com/proyectofinal/entidades/Inmueble.java
}
