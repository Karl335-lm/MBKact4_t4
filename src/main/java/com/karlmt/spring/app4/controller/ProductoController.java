package com.karlmt.spring.app4.controller;

import com.karlmt.spring.app4.dto.ProductoRequestDTO;
import com.karlmt.spring.app4.dto.ProductoResponseDTO;
import com.karlmt.spring.app4.service.ProductoService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Listar productos con paginación
    @GetMapping
    public ResponseEntity<Page<ProductoResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "nombre") Pageable pageable) {

        return ResponseEntity.ok(productoService.listar(pageable));
    }

    // Buscar producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    // Registrar producto
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> guardar(
            @Valid @RequestBody ProductoRequestDTO producto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.guardar(producto));
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO producto) {

        return ResponseEntity.ok(
                productoService.actualizar(id, producto));
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        productoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}