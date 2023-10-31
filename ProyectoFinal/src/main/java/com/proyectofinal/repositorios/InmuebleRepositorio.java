package com.proyectofinal.repositorios;

import com.proyectofinal.entidades.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InmuebleRepositorio extends JpaRepository<Inmueble, String> {

    @Query("SELECT i FROM Inmueble i WHERE " +
            "(LOWER(i.direccion) LIKE LOWER(CONCAT('%', :ubicacion, '%')) OR " +
            "LOWER(i.ciudad) LIKE LOWER(CONCAT('%', :ubicacion, '%')) OR " +
            "LOWER(i.provincia) LIKE LOWER(CONCAT('%', :ubicacion, '%')))")
    List<Inmueble> buscarPorUbicacion(@Param("ubicacion") String ubicacion);
    @Query("SELECT i FROM Inmueble i WHERE " +
            "(:ubicacion IS NULL OR i.ciudad LIKE %:ubicacion% OR i.provincia LIKE %:ubicacion%) " +
            "AND (:transaccion IS NULL OR i.transaccion = :transaccion) " +
            "AND (:tipoInmueble IS NULL OR i.tipoInmueble = :tipoInmueble) " +
            "AND (:ciudad IS NULL OR i.ciudad LIKE %:ciudad%) " +
            "AND (:provincia IS NULL OR i.provincia LIKE %:provincia%)")
    List<Inmueble> findInmueblesByFiltros(@Param("ubicacion") String ubicacion,
                                          @Param("transaccion") String transaccion,
                                          @Param("tipoInmueble") String tipoInmueble,
                                          @Param("ciudad") String ciudad,
                                          @Param("provincia") String provincia);



}
