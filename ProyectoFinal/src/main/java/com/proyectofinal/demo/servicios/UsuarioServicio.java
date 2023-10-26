
package com.proyectofinal.demo.servicios;

import com.proyectofinal.demo.entidades.Usuario;
import com.proyectofinal.demo.repositorios.UsuarioRepositorio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    
    public List<Usuario>listarUsuarios(){
        
        
        return null;
        
    }
    
}
