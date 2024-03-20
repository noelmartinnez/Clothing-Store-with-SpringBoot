package tiendaropa.repository;

import tiendaropa.model.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends CrudRepository<Producto, Long> {
    public List<Producto> findAll();

    List<Producto> findByDestacadoIsTrue();

    List<Producto> findByCategoriaid(Long categoriaid);
}
