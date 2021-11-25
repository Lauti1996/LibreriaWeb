/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreriaWeb.controladores;

import java.util.List;
import libreriaWeb.entidades.Editorial;
import libreriaWeb.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/listar")
    public String listarEditoriales(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listarEditorailes();
        modelo.put("editoriales", editoriales);
        return "listarEditoriales.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        try {
            Editorial e = editorialServicio.buscarPorId(id);
            modelo.put("editorial", e);
            return "modificarEditorial.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/editorial/listar";
        }

    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) {
        try {
            editorialServicio.modificarEditorial(id, nombre);
            return "redirect:/editorial/listar";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return modificar(modelo, id);
        }

    }

    @GetMapping("/cargar")
    public String cargar() {
        return "cargarEditorial.html";
    }

    @PostMapping("/cargar")
    public String cargar(ModelMap modelo, @RequestParam String nombre) {
        try {
            editorialServicio.cargarEditorial(nombre);
            return "redirect:/editorial";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "cargarEditorial.html";
        }

    }

    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id) {
        try {
            editorialServicio.darDeAltaALaEditorial(id);
            return "redirect:/editorial/listar";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/editorial/listar";
        }

    }

    @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable String id) {
        try {
            editorialServicio.darDeBajaALaEditorial(id);
            return "redirect:/editorial/listar";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/editorial/listar";
        }

    }
}
