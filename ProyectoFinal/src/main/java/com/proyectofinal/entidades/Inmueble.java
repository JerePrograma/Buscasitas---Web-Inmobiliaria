
package com.proyectofinal.entidades;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Inmueble implements Serializable {
    
    @Id
    private String cuentaTributaria;
    private String tipoInmueble;//input front casa, departamento, cochera, etc o enum
    private String direccion;
    private String ciudad;
    private String provincia;
    private String transaccion; // input o enum . compra, venta, alquiler anual, alquiler temporario o enum
    private List listaOfertas;
    
    
    @Temporal(TemporalType.DATE) 
    private Calendar citaDia; 
    
    @Temporal(TemporalType.TIME)
    private Time citaHora;
    
    
}
