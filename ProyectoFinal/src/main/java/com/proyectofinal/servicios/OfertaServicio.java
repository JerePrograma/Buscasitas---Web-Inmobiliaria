package com.proyectofinal.servicios;

import com.proyectofinal.entidades.Inmueble;
import com.proyectofinal.entidades.Oferta;
import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.repositorios.InmuebleRepositorio;
import com.proyectofinal.repositorios.OfertaRepositorio;
import com.proyectofinal.repositorios.UsuarioRepositorio;
import java.time.LocalDate;
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
    public void realizarOferta(String cuentaTributaria, String idUsuario, Integer valorOferta, LocalDate fechaOferta) {
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

    @Transactional
    public void aceptarOferta(String cuentaTributaria, String idOferta, LocalDate fechaOferta, LocalDate fechaAceptacion, Integer valorOferta) {
        // Aceptacion oferta debe tener un plazo, por ejemplo, 3 días
        // creo una variable que refleje la fecha de la aceptacion oferta
        // no inclui cuenta tributaria porque ya esta asociado al idOferta cuando se creo la oferta SUPONGO

        Optional<Inmueble> respuestaInmueble = inmuebleRepositorio.findById(cuentaTributaria);
        Optional<Oferta> respuestaOferta = ofertaRepositorio.findById(idOferta);

        if (respuestaInmueble.isPresent() && respuestaOferta.isPresent()) {
            Inmueble inmueble = respuestaInmueble.get(); // Obtengo inmueble
            Oferta oferta = respuestaOferta.get();//obtengo oferta

            LocalDate validezOferta = fechaOferta.plusDays(3); // Calculo de la oferta válida (3 días después de la fecha de oferta)
            LocalDate fechaAceptacionHoy = LocalDate.now();

            oferta.setFechaOferta(fechaOferta);
            if (LocalDate.now().isBefore(validezOferta)) {
                oferta.setEstadoOferta("Aceptada"); // Oferta aceptada
                oferta.setFechaAceptacion(fechaAceptacionHoy);
                ofertaRepositorio.save(oferta);
                System.out.println("La oferta fue aceptada");
            } else {
                oferta.setEstadoOferta("Vencida"); // Oferta caducada
                ofertaRepositorio.save(oferta);
                System.out.println("La oferta ha caducado");
            }
        }
    }

    // va en el Perfil de usuario Dentro de Oferta, Podria hacer ofertas enviadas, aceptadas, revocadas, caducas.
    @Transactional(readOnly = true)
    public List<Oferta> mostrarOfertasPorInmueble() {
        return ofertaRepositorio.findAll();
    }

    // elimina la oferta del repositorio al clickear REVOCAR OFERTA. PODRIA SER OFERTA IRREVOCABLES Y NO TENDRIAMOS ESTE
    //METODO.
    @Transactional
    public void revocarOferta(String cuentaTributaria, String idOferta, Integer valorOferta, LocalDate fechaRevocacion) {

        Optional<Inmueble> respuestaInmueble = inmuebleRepositorio.findById(cuentaTributaria);
        Optional<Oferta> respuestaOferta = ofertaRepositorio.findById(idOferta);

        if (respuestaInmueble.isPresent()) {
            Inmueble inmueble = respuestaInmueble.get(); //obtengo inmueble

        }

        if (respuestaOferta.isPresent()) {
            Oferta oferta = respuestaOferta.get();
            oferta.setFechaRevocacion(fechaRevocacion);
            oferta.setEstadoOferta("Revocada"); // revocada
            ofertaRepositorio.save(oferta); // se mantiene en el repositorio pero con otro estadao
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
