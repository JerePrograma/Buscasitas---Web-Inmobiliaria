
package com.proyectofinal.repositorios;

import com.proyectofinal.entidades.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InmuebleRepositorio extends JpaRepository<Inmueble, String>{ 

    // busqueda por tipo inmueble 
    @Query ("SELECT i FROM Inmueble i WHERE i.tipoInmueble =  :tipoInmueble")
    public Inmueble buscarPorTipoInmueble(@Param("tipoInmueble")String tipoInmueble);
        
    }
    

