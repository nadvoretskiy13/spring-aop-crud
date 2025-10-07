package ru.example.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.entity.Product;
import ru.example.repository.ProductRepository;
import ru.example.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
  private final ProductRepository repo;
  public ProductServiceImpl(ProductRepository repo) { this.repo = repo; }

  @Override
  public Product create(Product p) { return repo.save(p); }

  @Override
  public Optional<Product> getById(Long id) { return repo.findById(id); }

  @Override
  public List<Product> getAll() { return repo.findAll(); }

  @Override
  public Product update(Long id, Product p) {
    return repo.findById(id).map(existing -> {
      existing.setName(p.getName());
      existing.setDescription(p.getDescription());
      existing.setPrice(p.getPrice());
      return repo.save(existing);
    }).orElseThrow(() -> new RuntimeException("Product not found: " + id));
  }

  @Override
  public void delete(Long id) { repo.deleteById(id); }
}
