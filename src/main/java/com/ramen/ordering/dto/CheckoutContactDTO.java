package com.ramen.ordering.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutContactDTO {
  @NotBlank(message = "Name is required")
  private String name;

  private String email; // Optional
  @NotBlank(message = "Phone number is required")
  @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number should be valid (e.g., +998901234567)")
  private String phone;
  private String deliveryAddress;
}