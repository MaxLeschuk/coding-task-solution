package com.coding.task.service;

import com.coding.task.repository.model.ObjectData;

public interface CsvRecordStreamConsumer {

  void accept(ObjectData objectData);

  void end();
}
