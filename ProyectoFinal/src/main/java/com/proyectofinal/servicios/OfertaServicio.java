package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.entidades.Oferta;
import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.repositorios.InmuebleRepositorio;
import com.proyectofinal.repositorios.OfertaRepositorio;
import com.proyectofinal.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfertaServicio {

    @Autowired
    private OfertaRepositorio ofertaRepositorio;

    @Autowired
    private InmuebleRepositorio inmuebleRepositorio; //lo llamo porque la oferta esta asociada al inmueble

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void realizarOferta(String cuentaTributaria, String idUsuario, Integer valorOferta, Date fechaOferta) {
        //VALIDAR DATOS CON METODO 

        Optional<Inmueble> respuestaInmueble = inmuebleRepositorio.findById(cuentaTributaria);
        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(idUsuario);

        Inmueble inmueble = new Inmueble(); //creo un inmueble
        Usuario usuario = new Usuario();

        if (respuestaInmueble.isPresent()) {
            inmueble = respuestaInmueble.get();
        }

        if (respuestaUsuario.isPresent()) {
            usuario = respuestaUsuario.get();
        }
        Oferta oferta = new Oferta(); // se crea una oferta
        //    oferta.setIdOferta(idOferta); // se genera id de oferta
        oferta.setUsuario(usuario);
        oferta.setInmueble(inmueble);
        oferta.setFechaOferta(fechaOferta);//se fija la fecha oferta
        oferta.setValorOferta(valorOferta);//se establece el valor oferta que puede ser o no igual al solicitado por Ente o duenio
        oferta.setEstadoOferta("Enviada");// aqui el estado seria enviada.

        ofertaRepositorio.save(oferta);

    }

    // busca en repositorio oferta
    public Oferta getOne(String idOferta) {
        return ofertaRepositorio.getOne(idOferta);
    }

//    public void aceptarOferta(String cuentaTributaria, String idOferta, Date fechaOferta, Integer valorOferta) {
//        // Aceptación de oferta debe tener un plazo, por ejemplo, 3 días.
//
//        Optional<Inmueble> respuestaInmueble = inmuebleRepositorio.findById(cuentaTributaria);
//        Optional<Oferta> respuestaOferta = ofertaRepositorio.findById(idOferta);
//
//        if (respuestaInmueble.isPresent() && respuestaOferta.isPresent()) {
//            Inmueble inmueble = respuestaInmueble.get(); // Obtengo inmueble.
//            Oferta oferta = respuestaOferta.get(); // Obtengo oferta.
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(fechaOferta);
//            calendar.add(Calendar.DAY_OF_MONTH, 10); // Añadimos 10 días a la fecha de oferta para obtener la validez.
//            Date validezOferta = calendar.getTime();
//
//            oferta.setFechaOferta(fechaOferta);
//            Date fechaAceptacionHoy = new Date();
//
//            if (fechaAceptacionHoy.before(validezOferta)) {
//                oferta.setEstadoOferta("Aceptada"); // Oferta aceptada.
//                oferta.setFechaAceptacion(fechaAceptacionHoy);
//                ofertaRepositorio.save(oferta);
//                System.out.println("La oferta fue aceptada");
//            } else {
//                oferta.setEstadoOferta("Vencida"); // Oferta caducada.
//                ofertaRepositorio.save(oferta);
//                System.out.println("La oferta ha caducado");
//            }
//        }
//    }
// va en el Perfil de usuario Dentro de Oferta, Podria hacer ofertas enviadas, aceptadas, revocadas, caducas.
    @Transactional(readOnly = true)
    public List<Oferta> mostrarOfertasPorInmueble(String cuentaTributaria) {
        return ofertaRepositorio.buscarPorIdCuentaTributaria(cuentaTributaria);
    }

    // elimina la oferta del repositorio al clickear REVOCAR OFERTA. PODRIA SER OFERTA IRREVOCABLES Y NO TENDRIAMOS ESTE
    //METODO.
    @Transactional
    public void revocarOferta(String idOferta, Integer valorOferta) {

        Optional<Oferta> respuestaOferta = ofertaRepositorio.findById(idOferta);
        Date fechaRevocacion = new Date();

        // No es necesario comprobar si el inmueble está presente
        // si no se va a hacer nada con él en este método.
        if (respuestaOferta.isPresent()) {
            Oferta oferta = respuestaOferta.get();
            oferta.setFechaRevocacion(fechaRevocacion);
            oferta.setEstadoOferta("Revocada"); // Estado cambiado a revocada.
            ofertaRepositorio.save(oferta); // Se guarda la oferta con el estado actualizado.
            System.out.println("La oferta ha sido revocada");
        } else {
            System.out.println("No hay oferta formulada para revocar");
        }
    }

//    public void validarDatosOferta(Inmueble cuentaTributaria, Integer valorOferta, Date fechaOferta, List estadoOferta) throws MiExcepcion {
//        
//        if (cuentaTributaria == null || cuentaTributaria.isEmpty()) {
//            throw new MiExcepcion("El cuentaTributaria no puede estar vacío o ser nulo");
//        }
//        if (valorOferta == null) {
//            throw new MiExcepcion("El transaccion no puede estar vacío ");
//        }
//        if (fechaOferta == null || fechaOferta.isEmpty()) {
//            throw new MiExcepcion("La fecha de la oferta no puede estar vacío o ser nulo");
//        }
//        if (estadoOferta == null || estadoOferta.isEmpty()) {
//            throw new MiExcepcion("El estado de la oferta no puede estar vacío o ser nulo");
//        }
//       
//        }
}
