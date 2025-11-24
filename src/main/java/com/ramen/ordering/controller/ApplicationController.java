package com.ramen.ordering.controller;

import com.ramen.ordering.dto.ApplicationDTO;
import com.ramen.ordering.entity.Application;
import com.ramen.ordering.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Tag(name = "Application Management", description = "Endpoints for managing feedback and complaints")
public class ApplicationController {

  private final ApplicationService applicationService;

  @GetMapping
  @Operation(summary = "Get all applications", description = "Retrieve a list of all applications (feedback/complaints)")
  public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
    List<ApplicationDTO> applications = applicationService.getAllApplications();
    return ResponseEntity.ok(applications);
  }

  @GetMapping("/type/{type}")
  @Operation(summary = "Get applications by type", description = "Retrieve applications by type (FEEDBACK or COMPLAINT)")
  public ResponseEntity<List<ApplicationDTO>> getApplicationsByType(@PathVariable Application.ApplicationType type) {
    List<ApplicationDTO> applications = applicationService.getApplicationsByType(type);
    return ResponseEntity.ok(applications);
  }

  @GetMapping("/status/{status}")
  @Operation(summary = "Get applications by status", description = "Retrieve applications by status (PENDING, IN_PROGRESS, RESOLVED, CLOSED)")
  public ResponseEntity<List<ApplicationDTO>> getApplicationsByStatus(@PathVariable Application.ApplicationStatus status) {
    List<ApplicationDTO> applications = applicationService.getApplicationsByStatus(status);
    return ResponseEntity.ok(applications);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get application by ID", description = "Retrieve a specific application by its ID")
  public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long id) {
    ApplicationDTO application = applicationService.getApplicationById(id);
    return application != null ? ResponseEntity.ok(application) : ResponseEntity.notFound().build();
  }

  @PostMapping
  @Operation(summary = "Create a new application", description = "Create a new feedback or complaint application")
  public ResponseEntity<ApplicationDTO> createApplication(@RequestBody ApplicationDTO applicationDTO) {
    ApplicationDTO createdApplication = applicationService.createApplication(applicationDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdApplication);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an application", description = "Update an existing application by its ID")
  public ResponseEntity<ApplicationDTO> updateApplication(@PathVariable Long id, @RequestBody ApplicationDTO applicationDTO) {
    ApplicationDTO updatedApplication = applicationService.updateApplication(id, applicationDTO);
    return updatedApplication != null ? ResponseEntity.ok(updatedApplication) : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an application", description = "Delete an application by its ID")
  public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
    boolean deleted = applicationService.deleteApplication(id);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}