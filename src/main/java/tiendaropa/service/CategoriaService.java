package tiendaropa.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tiendaropa.dto.CategoriaData;
import tiendaropa.dto.UsuarioData;
import tiendaropa.model.*;
import tiendaropa.repository.CategoriaRepository;
import tiendaropa.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Transactional(readOnly = true)
    public List<Categoria> listadoCompletoCategoria(){
        return (List<Categoria>) categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CategoriaData findByNombre(String nombre) {
        Categoria categoria = categoriaRepository.findByNombre(nombre).orElse(null);
        if (categoria == null) return null;
        else {
            return modelMapper.map(categoria, CategoriaData.class);
        }
    }

    @Transactional
    public CategoriaData crearCategoria(CategoriaData categoria) {
        Optional<Categoria> categoriaBD = categoriaRepository.findByNombre(categoria.getNombre());
        if (categoriaBD.isPresent()) {
            throw new UsuarioServiceException("La categoría " + categoria.getNombre() + " ya existe");
        } else {
            // Crear una nueva instancia de Categoria y establecer el nombre antes de guardar
            Categoria categoriaNueva = new Categoria();
            categoriaNueva.setNombre(categoria.getNombre());
            categoriaNueva.setDescripcion(categoria.getDescripcion());
            categoriaNueva.setSubcategoriaid(categoria.getSubcategoriaid());

            categoriaRepository.save(categoriaNueva);
            return modelMapper.map(categoriaNueva, CategoriaData.class);
        }
    }

    @Transactional
    public void eliminarCategoria(Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);

        if (categoria == null) {
            throw new CategoriaServiceException("La Categoria con ID " + categoriaId + " no existe en la base de datos.");
        }

        // Recupera los productos asociados a la categoría
        List<Producto> productos = productoRepository.findByCategoriaid(categoriaId);

        // Actualiza los productos para que no tengan categoría o apunten a otra categoría
        for (Producto producto : productos) {
            producto.setCategoriaid(null); // O actualiza a otra categoría si es necesario
            productoRepository.save(producto);
        }

        // Elimina la categoría después de actualizar los productos
        categoriaRepository.delete(categoria);
    }



    @Transactional(readOnly = true)
    public List<Categoria> listadoCompleto(){
        return (List<Categoria>) categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria buscarCategoriaPorId(List<Categoria> categorias, Long idBuscado) {
        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(idBuscado)) {
                return categoria; // Devuelve el usuario si se encuentra
            }
        }
        return null; // Devuelve null si no se encuentra el usuario
    }

    @Transactional(readOnly = true)
    public CategoriaData findById(Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
        if (categoria == null) return null;
        else {
            return modelMapper.map(categoria, CategoriaData.class);
        }
    }

    @Transactional
    public CategoriaData actualizarCategoriaPorId(Long categoriaId, CategoriaData nuevosDatos) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(categoriaId);

        if (!categoriaExistente.isPresent()) {
            throw new CategoriaServiceException("La categoria con ID " + categoriaId + " no existe en la base de datos");
        }

        Categoria categoriaActualizado = categoriaExistente.get();

        // Actualiza los campos con los nuevos datos proporcionados
        if (nuevosDatos.getNombre() != null) {
            categoriaActualizado.setNombre(nuevosDatos.getNombre());
        }
        else{
            throw new CategoriaServiceException("Se ha recibido un nombre NULL");
        }

        if (nuevosDatos.getDescripcion() != null) {
            categoriaActualizado.setDescripcion(nuevosDatos.getDescripcion());
        }
        else{
            throw new CategoriaServiceException("Se ha recibido una descripcion NULL");
        }


        categoriaActualizado.setSubcategoriaid(nuevosDatos.getSubcategoriaid());
        categoriaActualizado = categoriaRepository.save(categoriaActualizado);

        return modelMapper.map(categoriaActualizado, CategoriaData.class);
    }

    @Transactional(readOnly = true)
    public Long obtenerIdPorNombre(String nombreCategoria) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findByNombre(nombreCategoria);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get().getId();
        } else {
            // Manejar el caso en el que la categoría no se encuentra
            return null;
        }
    }

}
