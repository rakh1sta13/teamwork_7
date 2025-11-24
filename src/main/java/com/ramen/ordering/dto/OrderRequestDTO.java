package com.ramen.ordering.dto;

import com.ramen.ordering.entity.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
  @NotNull(message = "Branch ID is required")
  private Long branchId;

  @NotBlank(message = "Client name is required")
  private String clientName;

  private String clientEmail; // Optional for backward compatibility

  @NotBlank(message = "Client phone number is required")
  @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number should be valid (e.g., +998901234567)")
  private String clientPhone;

  @NotNull(message = "Preferred date and time is required")
  private LocalDateTime preferredDateTime;

  @NotNull(message = "Payment method is required")
  private Order.PaymentMethod paymentMethod;

  private String deliveryAddress;
  private String notes;
  private List<OrderItemRequestDTO> orderItems;
}

