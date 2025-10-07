package ru.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.entity.Product;
import ru.example.service.ProductService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper mapper;
  @MockBean ProductService service;

  @Test
  void postProduct() throws Exception {
    Product p = new Product("Name", "Desc", 5.0);
    p.setId(1L);
    when(service.create(org.mockito.ArgumentMatchers.any())).thenReturn(p);

    mvc.perform(post("/api/products")
        .contentType("application/json")
        .content(mapper.writeValueAsString(p)))
      .andExpect(status().isCreated())
      .andExpect(header().exists("Location"))
      .andExpect(jsonPath("$.id").value(1));
  }
}
