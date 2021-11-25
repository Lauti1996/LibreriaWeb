/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreriaWeb.servicios;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javax.transaction.Transactional;
import libreriaWeb.entidades.Autor;
import libreriaWeb.entidades.Editorial;
import libreriaWeb.entidades.Libro;
import libreriaWeb.errores.ErrorServicio;
import libreriaWeb.repositorios.AutorRepositorio;
import libreriaWeb.repositorios.EditorialRepositorio;
import libreriaWeb.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    //CONSULTAS
    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public Libro buscarPorTitulo(String titulo) throws ErrorServicio {
        Libro libro = libroRepositorio.buscarPorTitulo(titulo);
        if (libro == null) {
            throw new ErrorServicio("No se encontró el libro solicitado.");
        }
        return libro;
    }

    public Libro buscarPorId(String id) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            return libro;
        } else {
            throw new ErrorServicio("No se encontró el libro solicitado.");
        }
    }

    public List<Libro> listarLibros() {
        List<Libro> libros = libroRepositorio.listarLibros();
        return libros;
    }

    //CARGAR UN LIBRO
    
    @Transactional
    public void cargarLibro(long isbn, String titulo, Integer anio, Integer ejemplares, Editorial editorial, Autor autor) throws ErrorServicio{
        if (isbn<=0){
            throw new ErrorServicio("El isbn no puede ser nulo o vacio.");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El título no puede ser nulo o vacio.");
        }
        if (anio < 1000 || anio == null) {
            throw new ErrorServicio("El año no puede ser nulo o menor a 1000.");
        }
        if (ejemplares == 0 || ejemplares == null) {
            throw new ErrorServicio("Mínimo tiene que entregar un ejemplar.");
        }
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresRestantes(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setAlta(true);        
        libro.setEditorial(editorial);
        libro.setAutor(autor);
        libroRepositorio.save(libro);
//        System.out.println("Estos son los autores cargados en nuestro sistema");
//        List<Autor> autores = autorServicio.listarAutores();
//        for (int i = 0; i < autores.size(); i++) {
//            System.out.println((i + 1) + ")" + autores.get(i).getNombre());
//        }
//        System.out.println("El autor del libro es alguno de ellos? Si - No");
//        String opc = leer.next().toUpperCase();
//        if (opc.equals("SI")) {
//            System.out.println("Ingrese el número del autor:");
//            int n = leer.nextInt();
//            libro.setAutor(autores.get(n - 1));
//        } else {
//            System.out.println("Ingrese el nombre del autor:");
//            String nombre = leer.next();
//            Autor autor = autorServicio.crearAutor(nombre);
//            libro.setAutor(autor);
//        }
//        System.out.println("Estas son las editoriales cargadas en nuestro sistema");
//        List<Editorial> editoriales = editorialServicio.listarEditorailes();
//        for (int i = 0; i < editoriales.size(); i++) {
//            System.out.println((i + 1) + ")" + editoriales.get(i).getNombre());
//        }
//        System.out.println("La editorial del libro es alguna de ellas? Si - No");
//        String opcion = leer.next().toUpperCase();
//        if (opcion.equals("SI")) {
//            System.out.println("Ingrese el número de la editorial:");
//            int num = leer.nextInt();
//            libro.setEditorial(editoriales.get(num - 1));
//        } else {
//            System.out.println("Ingrese el nombre de la editorial:");
//            String nombreE = leer.next();
//            Editorial editorial = editorialServicio.crearEditorial(nombreE);
//            libro.setEditorial(editorial);
//        }
        
        
    }

    //MODIFICAR LIBRO
    
    @Transactional
    public void modificarLibro(String id, long isbn, String titulo, Integer anio, Integer ejemplares, Editorial editorial, Autor autor) throws ErrorServicio{
        if (isbn<0){
            throw new ErrorServicio("El isbn no puede ser nulo o vacio.");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El título no puede ser nulo o vacio.");
        }
        if (anio < 1000 || anio == null) {
            throw new ErrorServicio("El año debe ser como mínimo 1000.");
        }
        if (ejemplares == 0 || ejemplares == null) {
            throw new ErrorServicio("Mínimo tiene que entregar un ejemplar.");
        }
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libroRepositorio.save(libro);
        }else{
            throw new ErrorServicio("No se encontró el libro para modificarlo.");
        }
    }
    
    //PRESTAMOS DE LIBROS
    
    @Transactional
    public void prestamoDeLibro(String id) throws ErrorServicio{
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            if(libro.getEjemplaresRestantes()==0){
                throw new ErrorServicio("No hay ejemplares disponibles del libro solicitado.");
            }else{
                libro.setEjemplaresRestantes(libro.getEjemplaresRestantes()-1);
                libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()+1);
                libroRepositorio.save(libro);
            }
        }else{
            throw new ErrorServicio("No se encontró el libro solicitado.");
        }
    }
    //DEVOLUCIÓN DE LIBROS
    
    @Transactional
    public void devolucionDeLibro(String id) throws ErrorServicio{
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes()+1);
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()-1);
            libroRepositorio.save(libro);
        }else{
            throw new ErrorServicio("No se encontró el libro a devolver.");
        }
    }
    //DAR DE BAJA
    
    @Transactional
    public void darDeBajaAUnLibro(String id) throws ErrorServicio{
        
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setAlta(false);
            libroRepositorio.save(libro);
        }else{
            throw new ErrorServicio("No se encontró el libro para darle de baja.");
        }
    }

    //DAR DE ALTA
    
    @Transactional
    public void darDeAltaAUnLibro(String id) throws ErrorServicio{
        
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setAlta(true);
            libroRepositorio.save(libro);
        }else{
            throw new ErrorServicio("No se encontró el libro para darle de alta.");
        }
    }
}


