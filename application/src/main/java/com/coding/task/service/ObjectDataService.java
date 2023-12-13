package com.coding.task.service;

import com.coding.task.repository.model.ObjectData;
import java.util.Optional;

public interface ObjectDataService {

  Optional<ObjectData> findById(String id);

  void deleteById(String id);
}
