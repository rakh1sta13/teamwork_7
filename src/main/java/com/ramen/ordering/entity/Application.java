package com.ramen.ordering.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column
  private String phone;

  @Enumerated(EnumType.STRING)
  @Column(name = "application_type", nullable = false)
  private ApplicationType applicationType;

  @Column(nullable = false, length = 1000)
  private String message;

  @Enumerated(EnumType.STRING)
  @Column(name = "application_status", nullable = false)
  private ApplicationStatus status = ApplicationStatus.PENDING;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public enum ApplicationType {
    FEEDBACK, COMPLAINT
  }

  public enum ApplicationStatus {
    PENDING, IN_PROGRESS, RESOLVED, CLOSED
  }
}