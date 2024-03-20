package tiendaropa.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tiendaropa.dto.ComentarioData;
import tiendaropa.dto.ProductoData;
import tiendaropa.model.Comentario;
import tiendaropa.model.Producto;
import tiendaropa.model.Usuario;
import tiendaropa.repository.ComentarioRepository;
import tiendaropa.repository.ProductoRepository;
import tiendaropa.repository.UsuarioRepository;

import java.util.Date;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository; // Asegúrate de tener el repositorio adecuado

    Logger logger = LoggerFactory.getLogger(ComentarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ComentarioData agregarComentario(Long usuarioId, Long productoId, String descripcion) {


        // Obtener el usuario y el producto desde la base de datos
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Producto producto = productoRepository.findById(productoId).orElse(null);
        Date fecha = new Date();

        Comentario comentario = new Comentario(descripcion,fecha,usuario, producto);
        comentarioRepository.save(comentario);
        return modelMapper.map(comentario, ComentarioData.class);
    }
}
