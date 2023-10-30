package com.proyectofinal.controladores;

import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.excepciones.MiExcepcion;
import com.proyectofinal.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

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
<<<<<<< HEAD
    public String registrar(ModelMap modelo)/*,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("direccion") String direccion,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("provincia") String provincia,
            @RequestParam("idCodigoTributario") String idCodigoTributario,
            @RequestParam("sexo") String sexo,
            @RequestParam("email") String email,
            @RequestParam("celular") String celular,
            @RequestParam("DNI") String DNI,
            @RequestParam("tipoPersona") String tipoPersona,
            @RequestParam("contrasenia") String contrasenia,
            @RequestParam("contrasenia2") String contrasenia2) throws MiExcepcion*/ {
//        usuarioServicio.registrarUsuario(idCodigoTributario, nombre, apellido, direccion, ciudad, provincia, DNI, sexo, email, celular, tipoPersona, contrasenia, contrasenia2);
        return "registro-form.html";
    }
=======
    public String registrar(ModelMap modelo) {
       
>>>>>>> 4dc6c07053c8f9a24178772038984ac6ae7e3672

    @GetMapping("/listar")
    public String listarUsuarios(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAllAttributes(usuarios);
        return "usuario_list.html";
    }
}
