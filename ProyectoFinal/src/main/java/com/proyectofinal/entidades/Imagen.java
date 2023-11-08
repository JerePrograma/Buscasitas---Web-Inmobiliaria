package com.proyectofinal.entidades;

import java.io.Serializable;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Imagen implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String mime;

    private String nombre;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    public byte[] contenido;

}
