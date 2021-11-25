/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreriaWeb.controladores;

import java.util.List;
import libreriaWeb.entidades.Autor;
import libreriaWeb.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/listar")
    public String autor(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        modelo.put("autores", autores);
        return "listarAutores.html";
    }
    
    
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id){
        try {
            Autor a = autorServicio.buscarPorId(id);
            modelo.put("autor", a);
            return "modificarAutor.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/autor/listar";
        }
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre){
        try {
            autorServicio.modificarAutor(id, nombre);
            return "redirect:/autor/listar";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return modificar(modelo, id); // revisar esto
        }
    }

    @GetMapping("/cargar")
    public String cargar() {
        return "cargarAutor.html";
    }

    @PostMapping("/cargar")
    public String cargar(ModelMap modelo, @RequestParam String nombre) {
        try {
            autorServicio.cargarAutor(nombre);
            return "redirect:/autor";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "cargarAutor.html";
        }

    }

    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id) {
        try {
            autorServicio.darDeAltaAUnAutor(id);
            return "redirect:/autor/listar";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/autor/listar";
        }

    }

    @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable String id) {
        try {
            autorServicio.darDeBajaAUnAutor(id);
            return "redirect:/autor/listar";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/autor/listar";
        }

    }
}
