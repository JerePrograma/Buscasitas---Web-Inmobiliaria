
package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.repositorios.UsuarioRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    
    public List<Usuario>listarUsuarios(){
        
        
        return null;
        
    }
    
}
