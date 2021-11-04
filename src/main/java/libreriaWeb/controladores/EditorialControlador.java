/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreriaWeb.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;
import libreriaWeb.errores.ErrorServicio;
import libreriaWeb.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditorialControlador {
    
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @PostMapping("/cargarEditorial")
    public String cargarEditorial(@RequestParam String nombre){
        try {
            
            editorialServicio.cargarEditorial(nombre);
            
        } catch (ErrorServicio ex) {
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "cargarEditorial.html";
        }
        return "index.html";
    }
}
