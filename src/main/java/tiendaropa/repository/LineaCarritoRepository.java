package tiendaropa.repository;

import org.springframework.data.repository.CrudRepository;
import tiendaropa.model.Carrito;
import tiendaropa.model.LineaCarrito;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import tiendaropa.model.Carrito;
import tiendaropa.model.LineaCarrito;

import java.util.List;

public interface LineaCarritoRepository extends CrudRepository<LineaCarrito, Long> {
    List<LineaCarrito> findByCarrito(Carrito carrito);

    @Transactional
    @Modifying
    @Query("DELETE FROM LineaCarrito l WHERE l.carrito.id = :carritoId")
    void deleteByCarritoId(Long carritoId);
}
