
package com.proyectofinal.demo.controladores;

import com.proyectofinal.demo.entidades.Usuario;
import com.proyectofinal.demo.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {
    @Autowired
   private UsuarioServicio usuarioServicio;
    
     @GetMapping("/") // funciona bien
    public String index() {
        return "index.html";
    }    
 
    @GetMapping("/registrar") 
    public String registrar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        
        
        return null;
        
    }
}
