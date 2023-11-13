package com.proyectofinal.entidades;

import com.proyectofinal.enumeraciones.Rol;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Usuario implements Serializable {

    @Id
    private String idCodigoTributario;//formulario select CUIL o CUIT. HECHO
    private String nombre;
    private String apellido;
    private String DNI;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String sexo; //formulario femenino o masculino. HECHO
    private String email;
    private String celular; // separar en codigo pais, provincia, numero?. HACE FALTA?
    private String tipoPersona; // formulario select persona humana o persona juridica. HECHO
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
