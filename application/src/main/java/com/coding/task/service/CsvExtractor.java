package com.coding.task.service;

import java.io.InputStream;

public interface CsvExtractor {

  void extract(InputStream inputStream, CsvRecordStreamConsumer callback);
}
