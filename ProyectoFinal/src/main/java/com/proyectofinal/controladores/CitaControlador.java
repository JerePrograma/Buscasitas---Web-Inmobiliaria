/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.proyectofinal.controladores;
//
//import com.proyectofinal.repositorios.CitaRepositorio;
//import com.proyectofinal.servicios.CitaServicio;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// *
// * @author matob
// */
//@Controller
//@RequestMapping("/cita")
//public class CitaControlador {
//    
//    @Autowired
//    CitaServicio citaServicio;
//    
//    @GetMapping("/registrar")
//    public String registrarCita(){
//        
//        return "cita_form.html";
//    }
//    
//    @PostMapping("/registro")
//    public String registrarCita(@RequestParam String idEnte,@RequestParam String idCliente,@RequestParam String idHorario,
//            @RequestParam(required = false) String nota, ModelMap modelo ){
//        try {
//             citaServicio.crearCita(idEnte, idCliente, idHorario, nota);
//             modelo.put("exito", "la cita fue cargada correctamente");
//        } catch (Exception e) {
//        }
//       
//        return "index.html";
//     }
//    
//    
//}
