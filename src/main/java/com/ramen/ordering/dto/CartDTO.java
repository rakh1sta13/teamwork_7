package com.ramen.ordering.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
  private List<CartItemDTO> items;
  private BigDecimal subtotal = BigDecimal.ZERO;
  private BigDecimal discount = BigDecimal.ZERO;
  private BigDecimal tax = BigDecimal.ZERO;
  private BigDecimal total = BigDecimal.ZERO;
}