/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreriaWeb.controladores;

import java.util.List;
import libreriaWeb.entidades.Autor;
import libreriaWeb.entidades.Editorial;
import libreriaWeb.entidades.Libro;
import libreriaWeb.servicios.AutorServicio;
import libreriaWeb.servicios.EditorialServicio;
import libreriaWeb.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/listar")
    public String libro(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();
        modelo.put("libros", libros);
        return "listarLibros.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        try {
            List<Autor> autores = autorServicio.listarAutores();
            modelo.put("autores", autores);
            List<Editorial> editoriales = editorialServicio.listarEditorailes();
            modelo.put("editoriales", editoriales);
            modelo.put("libro", libroServicio.buscarPorId(id));
            return "modificarLibro.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/libro";
        }

    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam long isbn, @RequestParam String titulo,
            @RequestParam Integer anio, @RequestParam Integer ejemplares,
            @RequestParam Editorial editorial, @RequestParam Autor autor) {
        try {
            libroServicio.modificarLibro(id, isbn, titulo, anio, ejemplares, editorial, autor);
            return "redirect:/libro/listar";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return modificar(modelo, id);
        }
    }

    @GetMapping("/cargar")
    public String cargar(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialServicio.listarEditorailes();
        modelo.put("editoriales", editoriales);
        return "cargarLibro.html";
    }

    @PostMapping("/cargar")
    public String cargar(ModelMap modelo, @RequestParam long isbn, @RequestParam String titulo,
            @RequestParam Integer anio, @RequestParam Integer ejemplares,
            @RequestParam Editorial editorial, @RequestParam Autor autor) {
        try {
            libroServicio.cargarLibro(isbn, titulo, anio, ejemplares, editorial, autor);

        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            List<Autor> autores = autorServicio.listarAutores();
            modelo.put("autores", autores);
            List<Editorial> editoriales = editorialServicio.listarEditorailes();
            modelo.put("editoriales", editoriales);
            return "cargarLibro.html";
        }
        return "redirect:/libro";
    }

    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id) {
        try {
            libroServicio.darDeAltaAUnLibro(id);
            return "redirect:/libro/listar";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/libro/listar";
        }

    }

    @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable String id) {
        try {
            libroServicio.darDeBajaAUnLibro(id);
            return "redirect:/libro/listar";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/libro/listar";
        }

    }

}
