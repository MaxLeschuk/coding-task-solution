package com.coding.task.repository.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "objectData")
@NoArgsConstructor
@AllArgsConstructor
public class ObjectData {

  @Id
  private String id;
  private String name;
  private String description;
  private Instant updatedTimestamp;
}
