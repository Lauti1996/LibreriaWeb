
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    
    @GetMapping("/libro")
    public String libro() {
        return "libro.html";
    }
    
    @GetMapping("/autor")
    public String autor() {
        return "autor.html";
    }
    
    @GetMapping("/editorial")
    public String editorial() {
        return "editorial.html";
    }
}
 