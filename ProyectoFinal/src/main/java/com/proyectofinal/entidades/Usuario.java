package com.proyectofinal.entidades;

import com.proyectofinal.enumeraciones.Rol;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Usuario implements Serializable {

    @Id
    private String idCodigoTributario;
    private String nombre;
    private String apellido;
    private String DNI;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String sexo; 
    private String email;
    private String celular; 
    private String tipoPersona;
    private String contrasenia;
    private String resetPwToken;
    private Boolean alta;
    

    @Enumerated
    private Rol rol;

    @OneToMany
    private List<Inmueble> propiedades;

    @OneToOne
    private Imagen imagen;
    
}
