package libreriaWeb.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import libreriaWeb.entidades.Editorial;
import libreriaWeb.errores.ErrorServicio;
import libreriaWeb.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    //CONSULTAS
    
    public Editorial buscarPorNombre(String nombre) throws ErrorServicio{
        Editorial editorial = editorialRepositorio.buscarPorNombre(nombre);
        if(editorial == null){
            throw new ErrorServicio("No se encontró la editorial buscada.");
        }
        return editorial;
    }
    
    public Editorial buscarPorId(String id) throws ErrorServicio{
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            return editorial;
        }else{
            throw new ErrorServicio("No se encontró la editorial solicitada.");
        }
    }
    
    public List<Editorial> listarEditorailes(){
        List<Editorial> editoriales = editorialRepositorio.listarEditoriales();
        return editoriales;
    }
    
    //CREAR EDITORIAL
    
    @Transactional
    public Editorial crearEditorial(String nombre) throws ErrorServicio{
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre no puede ser nulo o vacio.");
        }
        
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        editorialRepositorio.save(editorial);
        return editorial;
    }
    
    @Transactional
    public void cargarEditorial(String nombre) throws ErrorServicio{
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre no puede ser nulo o vacio.");
        }
        
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        editorialRepositorio.save(editorial);
    }
    
    //MODIFICAR EDITORIAL
    
    @Transactional
    public void modificarEditorial(String id, String nombre) throws ErrorServicio{
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre nuevo no puede ser nulo o vacio.");
        }
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }else{
            throw new ErrorServicio("No se encontró la editorial solicitada para modificar.");
        }
    }
    
    //DARLE DE BAJA
    
    @Transactional
    public void darDeBajaALaEditorial(String id) throws ErrorServicio{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            editorialRepositorio.save(editorial);
        }else{
            throw new ErrorServicio("No se encontró la editorial solicitada para darle de baja.");
        }
    }
    
    //DARLE DE ALTA
    
    @Transactional
    public void darDeAltaALaEditorial(String id) throws ErrorServicio{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(true);
            editorialRepositorio.save(editorial);
        }else{
            throw new ErrorServicio("No se encontró la editorial solicitada para darle de alta.");
        }
    }
}
