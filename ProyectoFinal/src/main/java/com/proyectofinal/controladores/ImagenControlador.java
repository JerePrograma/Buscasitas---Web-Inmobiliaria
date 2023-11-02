package com.proyectofinal.controladores;

import com.proyectofinal.entidades.Imagen;
import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.entidades.Usuario;
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

        @GetMapping("/inmueble/{cuentaTributaria}")
        public ResponseEntity<byte[]> imagenInmueble(@PathVariable String cuentaTributaria) {
            Inmueble inmueble = inmuebleServicio.getOne(cuentaTributaria);

            byte[] imagen = inmueble.getImagen().getContenido();

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);

        }

    }
