package tiendaropa.repository;

import org.springframework.data.repository.CrudRepository;
import tiendaropa.model.Carrito;
import tiendaropa.model.Categoria;
import tiendaropa.model.Usuario;

import java.util.Optional;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String s);

}
