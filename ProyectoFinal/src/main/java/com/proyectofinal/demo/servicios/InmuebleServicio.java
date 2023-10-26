
package com.proyectofinal.demo.servicios;

import com.proyectofinal.demo.entidades.Imagen;
import com.proyectofinal.demo.entidades.Inmueble;
import com.proyectofinal.demo.enumeraciones.TipoInmueble;
import com.proyectofinal.demo.repositorios.InmuebleRepositorio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InmuebleServicio {

    @Autowired
    private InmuebleRepositorio inmuebleRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public Inmueble guardarInmueble(MultipartFile archivo, String cuentaTributaria, String direccion, String ciudad, String provincia,
                                    String transaccion, List <String> listaOfertas, List <String> citaDiaHora,
                                    TipoInmueble tipoInmueble) throws Exception{
        Inmueble inmueble = new Inmueble();

        inmueble.setCuentaTributaria(cuentaTributaria);
        inmueble.setDireccion(direccion);
        inmueble.setCiudad(ciudad);
        inmueble.setProvincia(provincia);
        inmueble.setTransaccion(transaccion);
        inmueble.setListaOfertas(listaOfertas);
        inmueble.setCitaDiaHora(citaDiaHora);
        inmueble.setTipoInmueble(tipoInmueble);

        Imagen imagen = imagenServicio.guardarImagen(archivo);

        inmueble.setImagen(imagen);
        return inmuebleRepositorio.save(inmueble);
    }

    public Inmueble obtenerInmueblePorCuentaTributaria(String cuentaTributaria) {
        // Implementa la lógica para obtener un inmueble por su cuenta tributaria
        return inmuebleRepositorio.findById(cuentaTributaria).orElse(null);
    }

    public List<Inmueble> listarTodosLosInmuebles() {
        // Implementa la lógica para listar todos los inmuebles
        return inmuebleRepositorio.findAll();
    }

    @Transactional
    public Inmueble modificarInmueble(Inmueble inmueble) {
        // Verifica si el inmueble ya existe en la base de datos
        Inmueble inmuebleExistente = inmuebleRepositorio.findById(inmueble.getCuentaTributaria()).orElse(null);
        if (inmuebleExistente == null) {
            // El inmueble no existe, puedes manejar el error o lanzar una excepción
            // por ejemplo: throw new InmuebleNoEncontradoException("Inmueble no encontrado");
            return null;
        }

        // Actualiza los campos del inmueble existente con los valores del inmueble que se pasa como argumento
        inmuebleExistente.setDireccion(inmueble.getDireccion());
        inmuebleExistente.setCiudad(inmueble.getCiudad());
        inmuebleExistente.setProvincia(inmueble.getProvincia());
        inmuebleExistente.setTransaccion(inmueble.getTransaccion());
        // Actualiza otros campos según sea necesario

        // Guarda la entidad actualizada en la base de datos
        return inmuebleRepositorio.save(inmuebleExistente);
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

}

