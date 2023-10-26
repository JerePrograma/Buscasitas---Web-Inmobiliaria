<<<<<<<< HEAD:ProyectoFinal/src/main/java/com/proyectofinal/demo/repositorios/UsuarioRepositorio.java

package com.proyectofinal.demo.repositorios;


import com.proyectofinal.demo.entidades.Usuario;
========
package com.proyectofinal.repositorios;

import com.proyectofinal.entidades.Usuario;
>>>>>>>> 1af475c9326bac16537cf1d6fd921d6714f2a417:ProyectoFinal/src/main/java/com/proyectofinal/demo/repositorios/ClienteRepositorio.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.email =  :email")
    public Usuario buscarPorEmail(@Param("email") String email);

}