package com.coding.task.service.impl;

import com.coding.task.config.FilePersistingProperties;
import com.coding.task.repository.ObjectDataRepository;
import com.coding.task.repository.model.ObjectData;
import com.coding.task.service.CsvExtractor;
import com.coding.task.service.CsvRecordStreamConsumer;
import com.coding.task.service.FileUploadService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

  private final FilePersistingProperties filePersistingProperties;
  private final ObjectDataRepository objectDataRepository;
  private final CsvExtractor csvExtractor;
  private final Logger logger = Logger.getLogger(FileUploadServiceImpl.class);
  private final static String RECORDS_EXTRACTED = "Records extracted: %d";
  private final static String RECORD_DUPLICATE = "Duplicate record with PRIMARY_KEY: %s";

  @Override
  public void persistData(InputStream inputStream) {
    csvExtractor.extract(inputStream, new CsvRecordStreamConsumerImpl());
  }

  private void saveBatch(List<ObjectData> objectDataList) {
    logger.info(String.format(RECORDS_EXTRACTED, objectDataList.size()));
    try {
      objectDataRepository.saveAll(objectDataList);
    } catch (ConstraintViolationException e) {
      insertAvoidingDuplicates(objectDataList);
    }
    objectDataList.clear();
  }

  private void insertAvoidingDuplicates(List<ObjectData> records) {
    for (ObjectData record : records) {
      try {
        objectDataRepository.save(record);
      } catch (ConstraintViolationException e) {
        logger.warn(String.format(RECORD_DUPLICATE, record.getId()));
      }
    }
  }

  private final class CsvRecordStreamConsumerImpl implements CsvRecordStreamConsumer {

    private List<ObjectData> batch = new ArrayList<>();

    @Override
    public void accept(ObjectData objectData) {
      batch.add(objectData);
      if (batch.size() == filePersistingProperties.getBatchSize()) {
        saveBatch(batch);
        batch.clear();
      }
    }

    @Override
    public void end() {
      saveBatch(batch);
      batch.clear();
    }
  }
}
