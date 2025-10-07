package ru.example.service;
import ru.example.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
  Product create(Product p);
  Optional<Product> getById(Long id);
  List<Product> getAll();
  Product update(Long id, Product p);
  void delete(Long id);
}
