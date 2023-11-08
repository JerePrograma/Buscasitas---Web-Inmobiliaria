package com.proyectofinal.repositorios;

import com.proyectofinal.entidades.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InmuebleRepositorio extends JpaRepository<Inmueble, String> {

    @Query("SELECT i FROM Inmueble i WHERE "
            + "(:ubicacion IS NULL OR LOWER(i.direccion) LIKE LOWER(CONCAT('%', :ubicacion, '%')) OR LOWER(i.ciudad) LIKE LOWER(CONCAT('%', :ubicacion, '%')) OR LOWER(i.provincia) LIKE LOWER(CONCAT('%', :ubicacion, '%'))) "
            + "AND (:transaccion IS NULL OR LOWER(i.transaccion) LIKE LOWER(CONCAT('%', :transaccion, '%'))) "
            + "AND (:tipoInmueble IS NULL OR LOWER(i.tipoInmueble) LIKE LOWER(CONCAT('%', :tipoInmueble, '%'))) "
            + "AND (:ciudad IS NULL OR LOWER(i.ciudad) LIKE LOWER(CONCAT('%', :ciudad, '%'))) "
            + "AND (:provincia IS NULL OR LOWER(i.provincia) LIKE LOWER(CONCAT('%', :provincia, '%')))")
    List<Inmueble> findInmueblesByFiltros(@Param("ubicacion") String ubicacion,
                                          @Param("transaccion") String transaccion,
                                          @Param("tipoInmueble") String tipoInmueble,
                                          @Param("ciudad") String ciudad,
                                          @Param("provincia") String provincia);



}
