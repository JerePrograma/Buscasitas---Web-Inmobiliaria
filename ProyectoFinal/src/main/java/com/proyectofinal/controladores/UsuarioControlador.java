package com.proyectofinal.controladores;

import com.proyectofinal.excepciones.MiExcepcion;
import com.proyectofinal.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String registrar() {
        return "registro-form.html";
    }

    //registroControlador
    @PostMapping("/registro")
    public String registro(@RequestParam String idCodigoTributario, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String direccion, @RequestParam String ciudad, @RequestParam String provincia, @RequestParam String DNI, @RequestParam String sexo, @RequestParam String email, @RequestParam String celular, @RequestParam String tipoPersona, @RequestParam String contrasenia, @RequestParam String contrasenia2, ModelMap modelo) {
        try {
            usuarioServicio.registrarUsuario(idCodigoTributario, nombre, apellido, direccion, ciudad, provincia, DNI, sexo, email, celular, tipoPersona, contrasenia, contrasenia2);
            modelo.put("exito", "Usuario registrado correctamente");
            return "index.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("idCodigoTributario", idCodigoTributario);
            modelo.put("apellido", apellido);
            modelo.put("direccion", direccion);
            modelo.put("ciudad", ciudad);
            modelo.put("provincia", provincia);
            modelo.put("DNI", DNI);
            modelo.put("sexo", sexo);
            modelo.put("email", email);
            modelo.put("celular", celular);
            modelo.put("tipoPersona", tipoPersona);
            modelo.put("contrasenia", contrasenia);
            modelo.put("contrasenia2", contrasenia2);

            return "registro-form.html";
        }
    }
}
