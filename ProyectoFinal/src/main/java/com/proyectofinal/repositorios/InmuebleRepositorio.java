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
            + "AND (:provincia IS NULL OR LOWER(i.provincia) LIKE LOWER(CONCAT('%', :provincia, '%'))) "
            + "AND (:precioMinimo IS NULL OR i.precio  >= :precioMinimo) "
            + "AND (:precioMaximo IS NULL OR i.precio <= :precioMaximo) "
            + "AND (:moneda IS NULL OR LOWER(i.moneda) LIKE LOWER(CONCAT('%', :moneda, '%'))) "
            + "AND (:habitacionesMinimas IS NULL OR i.cantidadHabitaciones >= :habitacionesMinimas) "
            + "AND (:habitacionesMaximas IS NULL OR i.cantidadHabitaciones <= :habitacionesMaximas) "
            + "AND (:baniosMinimos IS NULL OR i.banios >= :baniosMinimos)"
            + "AND (:baniosMaximos IS NULL OR i.banios <= :baniosMaximos) "
            + "AND (:largoMinimo IS NULL OR i.largo >= :largoMinimo)"
            + "AND (:largoMaximo IS NULL OR i.largo <= :largoMaximo) "
            + "AND (:alturaMinima IS NULL OR i.altura >= :alturaMinima)"
            + "AND (:alturaMaxima IS NULL OR i.altura <= :alturaMaxima) ")
    List<Inmueble> findInmueblesByFiltros(@Param("ubicacion") String ubicacion,
            @Param("transaccion") String transaccion,
            @Param("tipoInmueble") String tipoInmueble,
            @Param("ciudad") String ciudad,
            @Param("provincia") String provincia,
            @Param("moneda") String moneda,
            @Param("precioMinimo") Integer precioMinimo,
            @Param("precioMaximo") Integer precioMaximo,
            @Param("habitacionesMinimas") Integer habitacionesMinimas,
            @Param("habitacionesMaximas") Integer habitacionesMaximas,
            @Param("baniosMinimos") Integer baniosMinimos,
            @Param("baniosMaximos") Integer baniosMaximos,
            @Param("largoMinimo") Integer largoMinimo,
            @Param("largoMaximo") Integer largoMaximo,
            @Param("alturaMinima") Integer alturaMinima,
            @Param("alturaMaxima") Integer alturaMaxima);

}
