package com.proyectofinal.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
//
///**
// *
// * @author invitado
// */

@Service
public class AppServicio {

    @Autowired
    private JavaMailSender mailSender;
//    
//    @Autowired
//    private Usuario usuario;
//
//    public String sendEmail(String toEmail, String subject, String body) {
//////usar mailSender acá…
////String from = "sender@gmail.com";//dirección de correo que hace el envío.
////String to = "recipient@gmail.com";//dirección de correo que recibe el
////
////public void sendEmail(String from, String to) {
//try{
//        //String resetLink =generateResetToken(usuario)
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("facio.gabrielemiliano@gmail.com");
//        message.setTo(usuario.getEmail());
//        message.setSubject("Buscasitas.com.ar - Servicio de Reestablecimiento de Credenciales");
//        message.setText("Este es un correo automático de Buscasitas.com.ar: Ingrese al siguiente link, de un solo uso, para restablecer su contraseña :" + resetLink + "Muchas gracias por utilizar nuestro servicio de restablecimiento de credenciales.");
//        mailSender.send(message); //método Send(envio), propio de Java Mail
//        System.out.println("Mail sent successfully...");
//
//    }
//}
}
