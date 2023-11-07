package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Imagen;
import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.excepciones.MiExcepcion;
import com.proyectofinal.repositorios.InmuebleRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class InmuebleServicio {

    @Autowired
    private InmuebleRepositorio inmuebleRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    RangoHorarioServicio rangoHorarioServicio;

    @Transactional
    public void registrarInmueble(MultipartFile archivo, String cuentaTributaria, String direccion, String ciudad, String provincia,
            String transaccion, String tipoInmueble, String tituloAnuncio,
            String descripcionAnuncio, Integer precioAlquilerVenta, String caracteristicaInmueble, String estado) throws Exception {
        validarDatos(archivo, cuentaTributaria, direccion, ciudad, provincia, transaccion, tipoInmueble, tituloAnuncio, descripcionAnuncio, precioAlquilerVenta, caracteristicaInmueble, estado);
        Inmueble inmueble = new Inmueble();

        inmueble.setCuentaTributaria(cuentaTributaria);
        inmueble.setDireccion(direccion);
        inmueble.setCiudad(ciudad);
        inmueble.setProvincia(provincia);
        inmueble.setTransaccion(transaccion);
        inmueble.setTipoInmueble(tipoInmueble);
        inmueble.setDescripcionAnuncio(descripcionAnuncio);
        inmueble.setPrecioAlquilerVenta(precioAlquilerVenta);
        inmueble.setCaracteristicaInmueble(caracteristicaInmueble);
        inmueble.setEstado(estado);
        inmueble.setTituloAnuncio(tituloAnuncio);
        inmueble.setAlta(true);

        Imagen imagen = imagenServicio.guardarImagen(archivo);

        inmueble.setImagen(imagen);

        // Asignar el rango horario al inmueble
        inmuebleRepositorio.save(inmueble);
    }

    @Transactional
    public void modificarInmueble(MultipartFile archivo, String cuentaTributaria,
            String tituloAnuncio, String descripcionAnuncio,
            String caracteristicaInmueble, String estado) throws Exception {
        // Verifica si el inmueble ya existe en la base de datos

        validarDatosModificar(cuentaTributaria, tituloAnuncio, descripcionAnuncio, caracteristicaInmueble, estado);

        Optional<Inmueble> respuesta = inmuebleRepositorio.findById(cuentaTributaria);
        if (respuesta.isPresent()) {
            Inmueble inmueble = respuesta.get();

            if (archivo != null) {
                String idImagen = null;

                idImagen = inmueble.getImagen().getId();

                Imagen imagen = imagenServicio.actualizar(archivo, idImagen);

                inmueble.setImagen(imagen);
            }

            inmueble.setTituloAnuncio(tituloAnuncio);

            inmueble.setDescripcionAnuncio(descripcionAnuncio);
            inmueble.setCaracteristicaInmueble(caracteristicaInmueble);
            inmueble.setEstado(estado);

            inmuebleRepositorio.save(inmueble);
        } else {
            System.out.println("No se ha encontrado una cuenta tributaria");
        }

    }

    public Inmueble getOne(String cuentaTributaria) {
        return inmuebleRepositorio.getOne(cuentaTributaria);
    }

    @Transactional(readOnly = true)
    public List<Inmueble> listarTodosLosInmuebles() {
        // Implementa la lógica para listar todos los inmuebles
        return inmuebleRepositorio.findAll();
    }

    public void darBajaInmueble(String cuentaTributaria) {
        Optional<Inmueble> respuesta = inmuebleRepositorio.findById(cuentaTributaria);
        if (respuesta.isPresent()) {
            Inmueble inmueble = respuesta.get();
            inmueble.setAlta(false);

            inmuebleRepositorio.save(inmueble);
        }
    }

    @Transactional
    public void eliminarInmueblePorCuentaTributaria(String cuentaTributaria) {
        // Implementa la lógica para eliminar un inmueble por su cuenta tributaria
        inmuebleRepositorio.deleteById(cuentaTributaria);
    }

    public List<Inmueble> buscarInmueblesPorFiltros(String ubicacion, String transaccion, String tipoInmueble, String ciudad, String provincia) {
        // Aquí construyes y ejecutas tu consulta personalizada utilizando el repositorio de InmuebleRepositorio.
        // Puedes usar las cláusulas WHERE en la consulta para aplicar los filtros necesarios.

        // Ejemplo de consulta con el uso de InmuebleRepositorio:
        return inmuebleRepositorio.findInmueblesByFiltros(ubicacion, transaccion, tipoInmueble, ciudad, provincia);
    }

    public Inmueble obtenerInmueblePorCuentaTributaria(String cuentaTributaria) {
        // Implementa la lógica para obtener un inmueble por su cuenta tributaria
        return inmuebleRepositorio.findById(cuentaTributaria).orElse(null);
    }

    public void validarDatos(MultipartFile archivo, String cuentaTributaria, String direccion, String ciudad, String provincia,
            String transaccion, String tipoInmueble, String tituloAnuncio,
            String descripcionAnuncio, Integer precioAlquilerVenta, String caracteristicaInmueble, String estado) throws MiExcepcion {
        if (archivo == null || archivo.isEmpty()) {
            throw new MiExcepcion("La imagen no puede estar vacío o ser nulo");
        }
        if (cuentaTributaria == null || cuentaTributaria.isEmpty()) {
            throw new MiExcepcion("El cuentaTributaria no puede estar vacío o ser nulo");
        }
        if (transaccion == null || transaccion.isEmpty()) {
            throw new MiExcepcion("El transaccion no puede estar vacío o ser nulo");
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
        if (tipoInmueble == null) {
            throw new MiExcepcion("El tipoInmueble no puede estar vacío o ser nulo");
        }
        if (tituloAnuncio == null || tituloAnuncio.isEmpty()) {
            throw new MiExcepcion("El tituloAnuncio no puede estar vacío o ser nulo");
        }
        if (descripcionAnuncio == null || descripcionAnuncio.isEmpty()) {
            throw new MiExcepcion("El descripcionAnuncio no puede estar vacío o ser nulo");
        }

        if (precioAlquilerVenta == null || precioAlquilerVenta <= 0) {
            throw new MiExcepcion("El tituloAnuncio no puede estar vacío o ser nulo");
        }
        if (caracteristicaInmueble == null || caracteristicaInmueble.isEmpty()) {
            throw new MiExcepcion("El caracteristicaInmueble no puede estar vacío o ser nulo");
        }
        if (estado == null) {
            throw new MiExcepcion("El estado no puede estar vacío o ser nulo");
        }
    }

    public void validarDatosModificar(String cuentaTributaria,
            String tituloAnuncio, String descripcionAnuncio, String caracteristicaInmueble, String estado) throws MiExcepcion {
        if (cuentaTributaria == null || cuentaTributaria.isEmpty()) {
            throw new MiExcepcion("El cuentaTributaria no puede estar vacío o ser nulo");
        }
        if (tituloAnuncio == null || tituloAnuncio.isEmpty()) {
            throw new MiExcepcion("El tituloAnuncio no puede estar vacío o ser nulo");
        }
        if (descripcionAnuncio == null || descripcionAnuncio.isEmpty()) {
            throw new MiExcepcion("El descripcionAnuncio no puede estar vacío o ser nulo");
        }

        if (caracteristicaInmueble == null || caracteristicaInmueble.isEmpty()) {
            throw new MiExcepcion("El caracteristicaInmueble no puede estar vacío o ser nulo");
        }
        if (estado == null) {
            throw new MiExcepcion("El estado no puede estar vacío o ser nulo");
        }
    }

}
