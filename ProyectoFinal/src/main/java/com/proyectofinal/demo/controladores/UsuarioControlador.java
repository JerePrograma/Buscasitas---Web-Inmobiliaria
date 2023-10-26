<<<<<<<< HEAD:ProyectoFinal/src/main/java/com/proyectofinal/demo/controladores/UsuarioControlador.java

package com.proyectofinal.demo.controladores;

import com.proyectofinal.demo.entidades.Usuario;
import com.proyectofinal.demo.servicios.UsuarioServicio;
========
package com.proyectofinal.controladores;

import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.servicios.ClienteServicio;
>>>>>>>> 1af475c9326bac16537cf1d6fd921d6714f2a417:ProyectoFinal/src/main/java/com/proyectofinal/demo/controladores/ClienteControlador.java
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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