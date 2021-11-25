/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreriaWeb.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import libreriaWeb.entidades.Autor;
import libreriaWeb.errores.ErrorServicio;
import libreriaWeb.repositorios.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    //CONSULTAS
    public Autor buscarPorNombre(String nombre) throws ErrorServicio {
        Autor autor = autorRepositorio.buscarPorNombre(nombre);
        if (autor == null) {
            throw new ErrorServicio("No se encontró al autor buscado");
        }
        return autor;
    }

    public Autor buscarPorId(String id) throws ErrorServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            return autor;
        } else {
            throw new ErrorServicio("No se encontró el autor solicitado.");
        }

    }

    public List<Autor> listarAutores() {
        List<Autor> autores = autorRepositorio.listarAutores();
        return autores;
    }

    //CREACIÓN DE AUTOR
    @Transactional //este lo invóco cuando cargo el libro y no está el autor en la DB
    public Autor crearAutor(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del autor no puede ser nulo.");
        }
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);
        autorRepositorio.save(autor);
        return autor;
    }

    @Transactional //lo invocó para cargar sólo el autor
    public void cargarAutor(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del autor no puede ser nulo.");
        }
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);
        autorRepositorio.save(autor);
    }

    //MODIFICAR AUTOR
    @Transactional
    public void modificarAutor(String id, String nombre) throws ErrorServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            if (nombre == null || nombre.isEmpty()) {
                throw new ErrorServicio("El nombre del autor no puede ser nulo.");
            }
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontró al autor para modificarlo.");
        }
    }

    //DAR DE BAJA A UN AUTOR
    @Transactional
    public void darDeBajaAUnAutor(String id) throws ErrorServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontró al autor para darle de baja.");
        }
    }

    //DAR DE ALTA A UN AUTOR
    @Transactional
    public void darDeAltaAUnAutor(String id) throws ErrorServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(true);
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontró al autor para darle de alta.");
        }
    }

}
