package ru.example.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.example.entity.Product;
import ru.example.repository.ProductRepository;
import ru.example.service.impl.ProductServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

  @Test
  void createAndGet() {
    ProductRepository repo = Mockito.mock(ProductRepository.class);
    ProductServiceImpl service = new ProductServiceImpl(repo);

    Product p = new Product("A", "B", 10.0);
    when(repo.save(any(Product.class))).thenAnswer(i -> {
      Product arg = i.getArgument(0);
      arg.setId(1L);
      return arg;
    });
    when(repo.findById(1L)).thenReturn(Optional.of(p));

    Product created = service.create(p);
    assertNotNull(created.getId());
    var opt = service.getById(1L);
    assertTrue(opt.isPresent());
    verify(repo, times(1)).save(any());
  }
}
