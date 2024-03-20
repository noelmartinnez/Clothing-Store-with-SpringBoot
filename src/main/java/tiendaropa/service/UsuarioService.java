package tiendaropa.service;

import tiendaropa.dto.UsuarioData;
import tiendaropa.model.*;
import tiendaropa.repository.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    public enum LoginStatus {LOGIN_OK, USER_NOT_FOUND, ERROR_PASSWORD}

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private LineaCarritoRepository lineaCarritoRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public LoginStatus login(String eMail, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(eMail);
        if (!usuario.isPresent()) {
            return LoginStatus.USER_NOT_FOUND;
        } else if (!usuario.get().getPassword().equals(password)) {
            return LoginStatus.ERROR_PASSWORD;
        } else {
            return LoginStatus.LOGIN_OK;
        }
    }

    // Se añade un usuario en la aplicación.
    // El email y password del usuario deben ser distinto de null
    // El email no debe estar registrado en la base de datos
    @Transactional
    public UsuarioData registrar(UsuarioData usuario) {
        Optional<Usuario> usuarioBD = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioBD.isPresent())
            throw new UsuarioServiceException("El usuario " + usuario.getEmail() + " ya está registrado");
        else if (usuario.getEmail() == null)
            throw new UsuarioServiceException("El usuario no tiene email");
        else if (usuario.getPassword() == null)
            throw new UsuarioServiceException("El usuario no tiene password");
        else {
            Usuario usuarioNuevo = modelMapper.map(usuario, Usuario.class);
            usuarioNuevo = usuarioRepository.save(usuarioNuevo);
            return modelMapper.map(usuarioNuevo, UsuarioData.class);
        }
    }

    @Transactional(readOnly = true)
    public UsuarioData findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, UsuarioData.class);
        }
    }

    @Transactional(readOnly = true)
    public UsuarioData findById(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, UsuarioData.class);
        }
    }

    // Método que devuelve el listado completo de objetos Usuario que hay en la base de datos.
    @Transactional(readOnly = true)
    public List<Usuario> listadoCompleto(){
        return (List<Usuario>) usuarioRepository.findAll();
    }

    // Método que busca un Usuario en una lista de Usuarios pasado por parámetro y un id concreto a buscar
    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorId(List<Usuario> usuarios, Long idBuscado) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(idBuscado)) {
                return usuario; // Devuelve el usuario si se encuentra
            }
        }
        return null; // Devuelve null si no se encuentra el usuario
    }

    // Método que actualiza los atributos de un usuario concreto por su ID
    @Transactional
    public UsuarioData actualizarUsuarioPorId(Long usuarioId, UsuarioData nuevosDatos) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuarioId);

        if (!usuarioExistente.isPresent()) {
            throw new UsuarioServiceException("El usuario con ID " + usuarioId + " no existe en la base de datos");
        }

        Usuario usuarioActualizado = usuarioExistente.get();

        // Actualiza los campos con los nuevos datos proporcionados
        if (nuevosDatos.getNombre() != null) {
            usuarioActualizado.setNombre(nuevosDatos.getNombre());
        }
        else{
            throw new UsuarioServiceException("Se ha recibido un nombre NULL");
        }

        if (nuevosDatos.getPassword() != null) {
            usuarioActualizado.setPassword(nuevosDatos.getPassword());
        }
        else{
            throw new UsuarioServiceException("Se ha recibido un password NULL");
        }

        if (nuevosDatos.getEmail() != null) {
            usuarioActualizado.setEmail(nuevosDatos.getEmail());
        }
        else{
            throw new UsuarioServiceException("Se ha recibido un email NULL");
        }

        usuarioActualizado.setApellidos(nuevosDatos.getApellidos());
        usuarioActualizado.setTelefono(nuevosDatos.getTelefono());
        usuarioActualizado.setPais(nuevosDatos.getPais());
        usuarioActualizado.setPoblacion(nuevosDatos.getPoblacion());
        usuarioActualizado.setDireccion(nuevosDatos.getDireccion());
        usuarioActualizado.setCodigopostal(nuevosDatos.getCodigopostal());


        usuarioActualizado = usuarioRepository.save(usuarioActualizado);

        return modelMapper.map(usuarioActualizado, UsuarioData.class);
    }

    @Transactional
    public void eliminarUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null) {
            throw new UsuarioServiceException("El usuario con ID " + usuarioId + " no existe en la base de datos.");
        }

        // Eliminar pedidos y líneas de pedido asociadas al usuario
        for (Pedido pedido : usuario.getPedidos()) {
            // Eliminar líneas de pedido asociadas al pedido
            lineaPedidoRepository.deleteAll(pedido.getLineaspedido()); // Eliminar líneas de pedido
        }

        Carrito carrito = usuario.getCarrito();
        lineaCarritoRepository.deleteAll(carrito.getLineascarrito()); // Eliminar líneas de carrito

        carritoRepository.delete(usuario.getCarrito()); // Eliminar carrito
        pedidoRepository.deleteAll(usuario.getPedidos()); // Eliminar pedidos
        comentarioRepository.deleteAll(usuario.getComentarios()); // Eliminar comentarios
        usuarioRepository.delete(usuario); // Eliminar usuario
    }
}
