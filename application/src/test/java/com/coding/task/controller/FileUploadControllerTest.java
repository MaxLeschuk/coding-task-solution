package com.coding.task.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.coding.task.repository.ObjectDataRepository;
import com.coding.task.service.CsvExtractor;
import com.coding.task.service.FileUploadService;
import java.io.FileInputStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@ImportAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class FileUploadControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  ObjectDataRepository objectDataRepository;
  @Autowired
  CsvExtractor csvExtractor;
  @Autowired
  FileUploadService fileUploadService;

  @Test
  void testFileUploading_shouldReturnOk() throws Exception {
    MockMultipartFile file = new MockMultipartFile("file", "file.csv", "text/csv",
        new FileInputStream("src/test/resources/mockdata/test.csv"));
    this.mockMvc.perform(
            multipart("/uploads")
                .file(file))
        .andExpect(status().isOk());

    verify(objectDataRepository, times(1)).saveAll(any());
  }

}