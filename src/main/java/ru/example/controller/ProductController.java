package ru.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.entity.Product;
import ru.example.service.ProductService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService service;
  public ProductController(ProductService service) { this.service = service; }

  @PostMapping
  public ResponseEntity<Product> create(@RequestBody Product p) {
    Product created = service.create(p);
    return ResponseEntity.created(URI.create("/api/products/" + created.getId())).body(created);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getById(@PathVariable Long id) {
    return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public List<Product> getAll() { return service.getAll(); }

  @PutMapping("/{id}")
  public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product p) {
    try {
      Product updated = service.update(id, p);
      return ResponseEntity.ok(updated);
    } catch (RuntimeException ex) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
