package com.coding.task.service.impl;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

import com.coding.task.exception.ReadingFileException;
import com.coding.task.repository.model.ObjectData;
import com.coding.task.service.CsvExtractor;
import com.coding.task.service.CsvRecordStreamConsumer;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CsvExtractorImpl implements CsvExtractor {

  private final Logger logger = Logger.getLogger(CsvExtractorImpl.class);
  private final static String INVALID_RECORD = "Invalid record: %s";

  @Override
  public void extract(InputStream inputStream, CsvRecordStreamConsumer callback) {
    try {
      CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT);

      csvRecords.stream()
          // to skip header
          .skip(1)
          .filter(this::isValidRecord)
          .map(this::mapObjectData)
          .forEach(callback::accept);

      callback.end();
    } catch (IOException e) {
      logger.warn(e.getLocalizedMessage());
      throw new ReadingFileException(e.getLocalizedMessage());
    }
  }

  private boolean isValidRecord(CSVRecord record) {
    if (record.size() == 4 && isNotBlank(record.get(0)) && isValidTimestamp(record.get(3))) {
      return true;
    }
    logger.warn(String.format(INVALID_RECORD, record));
    return false;
  }

  private boolean isValidTimestamp(String timestamp) {
    try {
      Instant.parse(timestamp);
      return true;
    } catch (DateTimeParseException exception) {
      return false;
    }
  }

  private ObjectData mapObjectData(CSVRecord csvRecord) {
    ObjectData objectData = new ObjectData();
    objectData.setId(csvRecord.get(0));
    objectData.setName(csvRecord.get(1));
    objectData.setDescription(csvRecord.get(2));
    objectData.setUpdatedTimestamp(Instant.parse(csvRecord.get(3)));
    return objectData;
  }

}
