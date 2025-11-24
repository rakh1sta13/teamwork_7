package com.ramen.ordering.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "branch_id", nullable = false)
  private Branch branch;

  @Column(name = "client_name", nullable = false)
  private String clientName;

  @Column(name = "client_phone", nullable = false)
  private String clientPhone;

  @Column(name = "client_email")
  private String clientEmail;

  @Column(name = "preferred_date_time", nullable = false)
  private LocalDateTime preferredDateTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_method", nullable = false)
  private PaymentMethod paymentMethod;

  @Enumerated(EnumType.STRING)
  @Column(name = "order_status", nullable = false)
  private OrderStatus status = OrderStatus.PENDING;

  @Column(name = "subtotal", nullable = false)
  private BigDecimal subtotal = BigDecimal.ZERO;

  @Column(name = "discount", nullable = false)
  private BigDecimal discount = BigDecimal.ZERO;

  @Column(name = "tax", nullable = false)
  private BigDecimal tax = BigDecimal.ZERO;

  @Column(name = "total", nullable = false)
  private BigDecimal total = BigDecimal.ZERO;

  @Column(name = "delivery_address")
  private String deliveryAddress;

  @Column(name = "notes")
  private String notes;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public enum PaymentMethod {
    PAYME, UZUM_BANK, VISA, MASTERCARD
  }

  public enum OrderStatus {
    PENDING, CONFIRMED, PREPARING, READY, DELIVERED, CANCELLED
  }
}