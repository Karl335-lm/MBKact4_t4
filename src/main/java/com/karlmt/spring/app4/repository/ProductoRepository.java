package com.karlmt.spring.app4.repository;

import com.karlmt.spring.app4.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}