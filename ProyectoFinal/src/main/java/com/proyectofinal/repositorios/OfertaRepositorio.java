package com.proyectofinal.repositorios;

import com.proyectofinal.entidades.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaRepositorio extends JpaRepository<Oferta, String> {

    //buscar oferta por inmueble
    // @Query("SELECT o FROM Oferta o WHERE o.oferta =  :oferta")
    //public Oferta buscarPorOferta(@Param("oferta") String oferta);
}
