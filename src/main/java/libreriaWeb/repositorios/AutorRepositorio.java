
package libreriaWeb.repositorios;

import java.util.List;
import libreriaWeb.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
    @Query("SELECT a FROM autor a WHERE a.nombre = :nombre")
    public Autor buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT a FROM autor a")
    public List<Autor> listarAutores();
    
    
    
    
}
