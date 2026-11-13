package com.karlmt.spring.app4.service;

import com.karlmt.spring.app4.dto.CategoriaRequestDTO;
import com.karlmt.spring.app4.dto.CategoriaResponseDTO;
import com.karlmt.spring.app4.models.Categoria;
import com.karlmt.spring.app4.repository.CategoriaRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Listar categorias
    public List<CategoriaResponseDTO> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(c -> new CategoriaResponseDTO(
                        c.getId(),
                        c.getNombre(),
                        c.getDescripcion()
                ))
                .toList();
    }

    // Buscar categoria por ID
    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria c = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        return new CategoriaResponseDTO(
                c.getId(),
                c.getNombre(),
                c.getDescripcion()
        );
    }

    // Registrar categoria
    public CategoriaResponseDTO guardar(CategoriaRequestDTO dto) {

        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categoria guardada = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(
                guardada.getId(),
                guardada.getNombre(),
                guardada.getDescripcion()
        );
    }

    // Actualizar categoria
    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categoria actualizada = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(
                actualizada.getId(),
                actualizada.getNombre(),
                actualizada.getDescripcion()
        );
    }

    // Eliminar categoria
    public void eliminar(Long id) {

        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada");
        }

        categoriaRepository.deleteById(id);
    }
}