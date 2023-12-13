package com.coding.task.service.impl;

import com.coding.task.repository.ObjectDataRepository;
import com.coding.task.repository.model.ObjectData;
import com.coding.task.service.ObjectDataService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ObjectDataServiceImpl implements ObjectDataService {

  private final ObjectDataRepository objectDataRepository;
  private final Logger logger = Logger.getLogger(ObjectDataServiceImpl.class);
  private final static String DELETE_MESSAGE = "Object data with id: %s was deleted";

  @Override
  public Optional<ObjectData> findById(String id) {
    return objectDataRepository.findById(id);
  }

  @Override
  public void deleteById(String id) {
    objectDataRepository.deleteById(id);
    logger.info(String.format(DELETE_MESSAGE, id));
  }
}
