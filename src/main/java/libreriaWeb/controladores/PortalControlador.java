/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreriaWeb.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lauta
 */
@Controller
@RequestMapping("/")
public class PortalControlador {
    
    
    
    ModelMap modelMap = new ModelMap();

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
}
