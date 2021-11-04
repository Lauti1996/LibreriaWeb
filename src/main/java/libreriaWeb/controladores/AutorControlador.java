/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreriaWeb.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;
import libreriaWeb.errores.ErrorServicio;
import libreriaWeb.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
    
    
    @PostMapping("/cargarAutor")
    public String cargarAutor(@RequestParam String nombre, @RequestParam String apellido){
        try {
            String nombreCompleto = nombre+" "+apellido;
            autorServicio.cargarAutor(nombreCompleto);
            
        } catch (ErrorServicio ex) {
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "cargarAutor.html"; 
        }
        return "index.html";
    }
}
