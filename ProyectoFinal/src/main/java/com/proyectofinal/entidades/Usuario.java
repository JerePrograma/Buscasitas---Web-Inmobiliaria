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

@Entity
@Data
@NoArgsConstructor

public class Usuario implements Serializable {

    @Id
    private String idCodigoTributario;//formulario select CUIL o CUIT
    private String nombre;
    private String apellido;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String DNI;
    private String sexo; //formulario femenino o masculino
    private String email;
    private String celular; // separar en codigo pais, provincia, numero?
    private String tipoPersona; // formulario select persona humana o persona juridica
    private String contrasenia;
    private Boolean alta;

    @Enumerated
    private Rol rol;

    @OneToMany
    private List<Inmueble> propiedades;
    
    @OneToOne
    private Imagen imagen;

}
