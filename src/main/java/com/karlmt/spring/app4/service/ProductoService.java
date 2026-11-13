package com.karlmt.spring.app4.service;

import com.karlmt.spring.app4.dto.ProductoRequestDTO;
import com.karlmt.spring.app4.dto.ProductoResponseDTO;
import com.karlmt.spring.app4.models.Categoria;
import com.karlmt.spring.app4.models.Producto;
import com.karlmt.spring.app4.repository.CategoriaRepository;
import com.karlmt.spring.app4.repository.ProductoRepository;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository) {

        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Convertir entidad a DTO
    private ProductoResponseDTO convertirDTO(Producto p) {

        return new ProductoResponseDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria().getId(),
                p.getCategoria().getNombre()
        );
    }

    // Listar productos con paginacion
    public Page<ProductoResponseDTO> listar(Pageable pageable) {

        return productoRepository.findAll(pageable)
                .map(this::convertirDTO);
    }

    // Buscar producto por ID
    public ProductoResponseDTO buscarPorId(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return convertirDTO(producto);
    }

    // Registrar producto
    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        Producto producto = new Producto();

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(categoria);

        return convertirDTO(productoRepository.save(producto));
    }

    // Actualizar producto
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(categoria);

        return convertirDTO(productoRepository.save(producto));
    }

    // Eliminar producto
    public void eliminar(Long id) {

        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }

        productoRepository.deleteById(id);
    }
}