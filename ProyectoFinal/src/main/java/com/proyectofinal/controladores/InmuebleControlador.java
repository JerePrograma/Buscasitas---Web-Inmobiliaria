
package com.proyectofinal.controladores;

import com.proyectofinal.servicios.InmuebleServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Inmueble")
public class InmuebleControlador {
    @Autowired
   private InmuebleServicio inmuebleServicio;
    
     @GetMapping("/") 
    public String index() {
        return "index.html";
    }    
}
