package com.ramen.ordering.dto;

import com.ramen.ordering.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
  private Long id;
  private Long branchId;
  private String branchName;
  private String clientName;
  private String clientEmail; // Optional for backward compatibility
  private String clientPhone;
  private LocalDateTime preferredDateTime;
  private Order.PaymentMethod paymentMethod;
  private Order.OrderStatus status = Order.OrderStatus.PENDING;
  private BigDecimal subtotal = BigDecimal.ZERO;
  private BigDecimal discount = BigDecimal.ZERO;
  private BigDecimal tax = BigDecimal.ZERO;
  private BigDecimal total = BigDecimal.ZERO;
  private String deliveryAddress;
  private String notes;
  private List<OrderItemDTO> orderItems;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}