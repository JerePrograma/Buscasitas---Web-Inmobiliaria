package com.proyectofinal.controladores;

import com.proyectofinal.entidades.Imagen;
import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.servicios.InmuebleServicio;
import com.proyectofinal.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    InmuebleServicio inmuebleServicio;

    @GetMapping("/inmueble/{cuentaTributaria}/principal")
    public ResponseEntity<byte[]> imagenPrincipalInmueble(@PathVariable String cuentaTributaria) {
        Inmueble inmueble = inmuebleServicio.obtenerInmueblePorCuentaTributaria(cuentaTributaria);
        Imagen imagen = inmueble.getImagenPrincipal();

        if (imagen != null) {
            byte[] imagenData = imagen.getContenido();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(imagen.getMime()));
            return new ResponseEntity<>(imagenData, headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/inmueble/{cuentaTributaria}/secundaria/{imagenId}")
    public ResponseEntity<byte[]> imagenSecundariaInmueble(
            @PathVariable String cuentaTributaria,
            @PathVariable String imagenId) {

        Inmueble inmueble = inmuebleServicio.obtenerInmueblePorCuentaTributaria(cuentaTributaria);

        for (Imagen imagen : inmueble.getImagenesSecundarias()) {
            if (imagen.getId().equals(imagenId)) {
                byte[] imagenData = imagen.getContenido();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(imagen.getMime()));
                return new ResponseEntity<>(imagenData, headers, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
