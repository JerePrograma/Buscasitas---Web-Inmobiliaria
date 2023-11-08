package com.proyectofinal.controladores;

import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.enumeraciones.Rol;
import com.proyectofinal.excepciones.MiExcepcion;
import com.proyectofinal.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PostMapping("/registrar")
    public String registro(@RequestParam("idCodigoTributario") String idCodigoTributario,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("direccion") String direccion,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("provincia") String provincia,
            @RequestParam("DNI") String DNI,
            @RequestParam("sexo") String sexo,
            @RequestParam("email") String email,
            @RequestParam("celular") String celular,
            @RequestParam("tipoPersona") String tipoPersona,
            @RequestParam("contrasenia") String contrasenia,
            @RequestParam("contrasenia2") String contrasenia2,
            ModelMap modelo) {
        try {
            usuarioServicio.registrarUsuario(idCodigoTributario, nombre, apellido, direccion, ciudad, provincia, DNI, sexo, email, celular, tipoPersona, contrasenia, contrasenia2);
            modelo.put("exito", "Usuario registrado correctamente");

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
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENTE','ROLE_ENTE')")
    @GetMapping("/perfil/{idCodigoTributario}")
    public String perfil(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        modelo.put("usuario", usuario);

        return "perfil.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENTE','ROLE_ENTE')")
    @GetMapping("/modificar/{idCodigoTributario}")
    public String modificar(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        modelo.put("usuario", usuario);

        return "perfil-modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN')")
    @PostMapping("/modificar/{idCodigoTributario}")
    public String modificar(@PathVariable("idCodigoTributario") String idCodigoTributario,
            @RequestParam("direccion") String direccion,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("provincia") String provincia,
            @RequestParam("sexo") String sexo,
            @RequestParam("email") String email,
            @RequestParam("celular") String celular,
            @RequestParam("tipoPersona") String tipoPersona,
                            @RequestParam("Rol") String rol,
            ModelMap modelo) {
        try {
            usuarioServicio.modificarUsuario(idCodigoTributario, direccion, ciudad, provincia,
                    sexo, email, celular, tipoPersona, rol);
            modelo.put("exito", "Usuario actualizado correctamente!");
            return "index.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("email", email);
            return "perfil-modificar.html";
        }
    }

    @GetMapping("/lista")
    public String listarUsuario(ModelMap modelo) throws Exception {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "usuario_lista";
    }

    @GetMapping("/eliminar/{idCodigoTributario}")
    public String eliminarUsuario(@PathVariable("idCodigoTributario") String idCodigoTributario,
            ModelMap modelo) throws Exception {
        usuarioServicio.eliminarUsuario(idCodigoTributario);

        return "index.html";
    }

    @GetMapping("/dar-baja/{idCodigoTributario}")
    public String darBajaUsuario(@PathVariable("idCodigoTributario") String idCodigoTributario,
            ModelMap modelo) throws Exception {
        usuarioServicio.darBajaUsuario(idCodigoTributario);

        return "index.html";
    }

    @GetMapping("/dar-alta/{idCodigoTributario}")
    public String darAltaUsuario(@PathVariable("idCodigoTributario") String idCodigoTributario,
            ModelMap modelo) throws Exception {
        usuarioServicio.darAltaUsuario(idCodigoTributario);

        return "index.html";
    }
}
