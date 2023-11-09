package com.proyectofinal.controladores;

import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.repositorios.UsuarioRepositorio;
import com.proyectofinal.servicios.InmuebleServicio;
import com.proyectofinal.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// PROYECTO FINAL - EQUIPO A - Buscasitas.com.ar
@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private InmuebleServicio inmuebleServicio;

    @GetMapping("/")
    public String index(ModelMap modelo, HttpSession session) {
        List<Inmueble> inmuebles = inmuebleServicio.listarTodosLosInmuebles();
        modelo.addAttribute("inmuebles", inmuebles);

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        modelo.put("usuario", usuario);
        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña inválidos!");
        }
        return "login.html";
    }
    
     @GetMapping("/mapa")
    public String mapa(@RequestParam(required = false) String error, ModelMap modelo) {
        
        return "mapa.html";
    }

     
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ENTE','ROLE_CLIENT', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        modelo.put("usuario", usuario);
        if (usuario.getRol().toString().equals("ADMIN")) {
            return "redirect:/inicio";
        }
        return "index.html";
    }
}