package com.proyectofinal.controladores;

import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class ClienteControlador {

    @Autowired
    private ClienteServicio usuarioServicio;

//    @GetMapping("/") // funciona bien
//    public String index() {
//        return "index.html";
//    }

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();

        return "form_usuario.html";
    }
}