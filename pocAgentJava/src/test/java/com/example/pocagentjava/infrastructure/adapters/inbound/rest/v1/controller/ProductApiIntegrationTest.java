package com.example.pocagentjava.infrastructure.adapters.inbound.rest.v1.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApiIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void supportsCreateReadUpdateListAndDelete() throws Exception {
    MvcResult created = mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"name":"Keyboard","description":"Mechanical","active":true}
                """))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.name").value("Keyboard"))
        .andReturn();

    String location = created.getResponse().getHeader("Location");
    String id = location.substring(location.lastIndexOf('/') + 1);

    mockMvc.perform(get("/api/v1/products/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.description").value("Mechanical"));

    mockMvc.perform(put("/api/v1/products/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"name":"Keyboard Pro","description":"Hot-swappable","active":false}
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Keyboard Pro"))
        .andExpect(jsonPath("$.active").value(false));

    mockMvc.perform(get("/api/v1/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));

    mockMvc.perform(delete("/api/v1/products/{id}", id))
        .andExpect(status().isNoContent());

    mockMvc.perform(get("/api/v1/products/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value("PRODUCT_NOT_FOUND"))
        .andExpect(jsonPath("$.path").value("/api/v1/products/" + id));
  }

  @Test
  void returnsStructuredValidationErrorForInvalidCreateRequest() throws Exception {
    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"name":" ","description":"Mechanical","active":true}
                """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
        .andExpect(jsonPath("$.details[0].field").value("name"));
  }
}
