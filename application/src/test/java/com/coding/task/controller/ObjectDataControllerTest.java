package com.coding.task.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.coding.task.repository.ObjectDataRepository;
import com.coding.task.repository.model.ObjectData;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ImportAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class ObjectDataControllerTest {

  private final static String ID = "id";
  @Autowired
  MockMvc mockMvc;
  @MockBean
  ObjectDataRepository objectDataRepository;
  @Autowired
  ObjectMapper objectMapper;

  @Test
  void testDeleteObject_ShouldReturn200() throws Exception {
    this.mockMvc.perform(delete("/records")
            .param("id", ID))
        .andExpect(status().isOk());
    verify(objectDataRepository, times(1)).deleteById(ID);
  }

  @Test
  void testFindById_shouldReturnObjectAndStatusOk() throws Exception {
    ObjectData objectData = new ObjectData("id", "name", "description", Instant.now());

    when(objectDataRepository.findById(ID)).thenReturn(
        Optional.of(objectData));

    String result = this.mockMvc.perform(get("/records")
            .param("id", ID))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    String expected = objectMapper.writeValueAsString(objectData);

    Assert.assertEquals("expected", result);
  }
}