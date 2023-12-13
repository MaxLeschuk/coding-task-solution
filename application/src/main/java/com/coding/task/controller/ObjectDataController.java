package com.coding.task.controller;

import com.coding.task.repository.model.ObjectData;
import com.coding.task.service.ObjectDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Records")
@RestController
@RequiredArgsConstructor
public class ObjectDataController {

  private final ObjectDataService objectDataService;

  @Operation(summary = "Delete record",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "500", description = "Internal server error occurred")})
  @DeleteMapping("/records")
  public ResponseEntity<Void> deleteObject(@RequestParam("id") String id) {
    objectDataService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Get record",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal server error occurred")})
  @GetMapping("/records")
  public ResponseEntity<ObjectData> findById(@RequestParam("id") String id) {
    Optional<ObjectData> objectData = objectDataService.findById(id);
    return objectData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

}
