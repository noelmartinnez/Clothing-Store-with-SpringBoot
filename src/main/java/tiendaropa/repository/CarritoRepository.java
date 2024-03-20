package tiendaropa.repository;

import org.springframework.data.repository.CrudRepository;
import tiendaropa.model.Carrito;
import tiendaropa.model.Usuario;

import java.util.Optional;

public interface CarritoRepository extends CrudRepository<Carrito, Long> {
    Optional<Carrito> findByUsuario(Usuario usuario);
}
