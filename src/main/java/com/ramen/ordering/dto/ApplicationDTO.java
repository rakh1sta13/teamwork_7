package com.ramen.ordering.dto;

import com.ramen.ordering.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
  private Long id;
  private String name;
  private String email;
  private String phone;
  private Application.ApplicationType applicationType;
  private String message;
  private Application.ApplicationStatus status = Application.ApplicationStatus.PENDING;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}