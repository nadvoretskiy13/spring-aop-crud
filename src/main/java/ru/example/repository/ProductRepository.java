package ru.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}
