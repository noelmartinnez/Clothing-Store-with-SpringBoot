package tiendaropa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tiendaropa.model.Pedido;
import tiendaropa.model.Usuario;
import tiendaropa.repository.LineaPedidoRepository;
import tiendaropa.repository.PedidoRepository;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;

    @Transactional(readOnly = true)
    public List<Pedido> listadoCompleto(){
        return (List<Pedido>) pedidoRepository.findAll();
    }

    @Transactional
    public void eliminarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);

        if (pedido == null) {
            throw new PedidoServiceException("El pedido con ID " + pedidoId + " no existe en la base de datos.");
        }

        // Eliminar líneas de pedido asociadas al pedido
        lineaPedidoRepository.deleteAll(pedido.getLineaspedido());

        // Eliminar la referencia del pedido en el usuario asociado
        Usuario usuario = pedido.getUsuario();
        if (usuario != null) {
            usuario.getPedidos().remove(pedido);
        }

        // Finalmente, eliminar el pedido
        pedidoRepository.delete(pedido);
    }

    @Transactional(readOnly = true)
    public Pedido buscarPedidoPorId(List<Pedido> pedidos, Long idBuscado) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId().equals(idBuscado)) {
                return pedido; // Devuelve el usuario si se encuentra
            }
        }
        return null; // Devuelve null si no se encuentra el usuario
    }
}
