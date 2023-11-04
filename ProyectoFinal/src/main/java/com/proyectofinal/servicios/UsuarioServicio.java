package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.enumeraciones.Rol;
import com.proyectofinal.excepciones.MiExcepcion;
import com.proyectofinal.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrarUsuario(String idCodigoTributario, String nombre, String apellido, String direccion, String ciudad, String provincia, String DNI, String sexo, String email, String celular, String tipoPersona, String contrasenia, String contrasenia2) throws MiExcepcion {

        validarDatos(idCodigoTributario, nombre, apellido, direccion, ciudad, provincia, DNI, sexo, email, celular, tipoPersona, contrasenia, contrasenia2);

        Usuario usuario = new Usuario();

        usuario.setApellido(apellido);
        usuario.setCelular(celular);
        usuario.setCiudad(ciudad);
        usuario.setDNI(DNI);
        usuario.setDireccion(direccion);
        usuario.setEmail(email);
        usuario.setIdCodigoTributario(idCodigoTributario);
        usuario.setNombre(nombre);
        usuario.setProvincia(provincia);
        usuario.setSexo(sexo);
        usuario.setTipoPersona(tipoPersona);
        usuario.setAlta(true);
        usuario.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));

        usuario.setRol(Rol.USER);

        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void modificarUsuario(String idCodigoTributario, String direccion, String ciudad, String provincia,
            String sexo, String email, String celular, String tipoPersona) throws MiExcepcion {

        validarDatos(idCodigoTributario, direccion, ciudad, provincia,
                sexo, email, celular, tipoPersona);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idCodigoTributario);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setDireccion(direccion);
            usuario.setCiudad(ciudad);
            usuario.setProvincia(provincia);
            usuario.setSexo(sexo);
            usuario.setEmail(email);
            usuario.setCelular(celular);
            usuario.setTipoPersona(tipoPersona);
            usuarioRepositorio.save(usuario);
        }
    }
//  Implementar este método cuando se aplique el registro de usuarios
//    @Override

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        }

        List<GrantedAuthority> permisos = new ArrayList<>();
        GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
        permisos.add(p);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("usuariosession", usuario);

        return new User(usuario.getEmail(), usuario.getContrasenia(), permisos);
    }

    @Transactional(readOnly = true)
    public Usuario getOne(String idCodigoTributario) {
        return usuarioRepositorio.getOne(idCodigoTributario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {

        return usuarioRepositorio.findAll();
    }

    @Transactional
    public void darBajaUsuario(String idCodigoTributario) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idCodigoTributario);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setAlta(false);

            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public void eliminarUsuario(String idCodigoTributario) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idCodigoTributario);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            usuarioRepositorio.delete(usuario);
        }
    }

    public void validarDatos(String idCodigoTributario, String nombre, String apellido, String direccion, String ciudad, String provincia, String DNI,
            String sexo, String email, String celular, String tipoPersona, String contrasenia, String contrasenia2) throws MiExcepcion {
        if (idCodigoTributario == null || idCodigoTributario.isEmpty()) {
            throw new MiExcepcion("El código tributario no puede estar vacío o ser nulo");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede estar vacío o ser nulo");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new MiExcepcion("El apellido no puede estar vacío o ser nulo");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new MiExcepcion("El direccion no puede estar vacío o ser nulo");
        }
        if (ciudad == null || ciudad.isEmpty()) {
            throw new MiExcepcion("El ciudad no puede estar vacío o ser nulo");
        }
        if (provincia == null || provincia.isEmpty()) {
            throw new MiExcepcion("El código no puede estar vacío o ser nulo");
        }
        if (DNI == null || DNI.isEmpty()) {
            throw new MiExcepcion("El DNI no puede estar vacío o ser nulo");
        }
        if (sexo == null || sexo.isEmpty()) {
            throw new MiExcepcion("El sexo no puede estar vacío o ser nulo");
        }
        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("El email no puede estar vacío o ser nulo");
        }
        if (celular == null || celular.isEmpty()) {
            throw new MiExcepcion("El celular no puede estar vacío o ser nulo");
        }
        if (tipoPersona == null || tipoPersona.isEmpty()) {
            throw new MiExcepcion("El tipoPersona no puede estar vacío o ser nulo");
        }
        if (contrasenia == null || contrasenia.isEmpty() || contrasenia.length() <= 5) {
            throw new MiExcepcion("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!contrasenia.equals(contrasenia2)) {
            throw new MiExcepcion("Las contraseñas ingresadas deben ser iguales");
        }
    }

    public void validarDatos(String idCodigoTributario, String direccion, String ciudad, String provincia, String sexo, String email,
            String celular, String tipoPersona) throws MiExcepcion {
        if (idCodigoTributario == null || idCodigoTributario.isEmpty()) {
            throw new MiExcepcion("El código tributario no puede estar vacío o ser nulo");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new MiExcepcion("El direccion no puede estar vacío o ser nulo");
        }
        if (ciudad == null || ciudad.isEmpty()) {
            throw new MiExcepcion("El ciudad no puede estar vacío o ser nulo");
        }
        if (provincia == null || provincia.isEmpty()) {
            throw new MiExcepcion("El código no puede estar vacío o ser nulo");
        }
        if (sexo == null || sexo.isEmpty()) {
            throw new MiExcepcion("El sexo no puede estar vacío o ser nulo");
        }
        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("El email no puede estar vacío o ser nulo");
        }
        if (celular == null || celular.isEmpty()) {
            throw new MiExcepcion("El celular no puede estar vacío o ser nulo");
        }
        if (tipoPersona == null || tipoPersona.isEmpty()) {
            throw new MiExcepcion("El tipoPersona no puede estar vacío o ser nulo");
        }
    }

}
