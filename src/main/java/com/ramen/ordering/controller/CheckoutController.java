package com.ramen.ordering.controller;

import com.ramen.ordering.dto.*;
import com.ramen.ordering.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
@Tag(name = "Checkout Process", description = "Endpoints for the checkout process")
public class CheckoutController {

  private final OrderService orderService;

  @PostMapping("/contact")
  @Operation(summary = "Checkout contact step", description = "Handle the contact information step in checkout")
  public ResponseEntity<CartDTO> processContact(@Valid @RequestBody CheckoutContactDTO contactDTO) {
    // In a real implementation, we would validate and store contact info temporarily
    // For now, we'll just return an empty cart as an example
    CartDTO cart = new CartDTO();
    return ResponseEntity.ok(cart);
  }

  @PostMapping("/summary")
  @Operation(summary = "Checkout summary", description = "Calculate and return order summary")
  public ResponseEntity<OrderDTO> calculateSummary(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
    // Calculate totals
    Map.Entry<java.math.BigDecimal, java.math.BigDecimal> calculation =
        orderService.calculateOrderTotal(orderRequestDTO.getOrderItems());
    java.math.BigDecimal subtotal = calculation.getKey();
    java.math.BigDecimal discount = calculation.getValue();
    java.math.BigDecimal tax = subtotal.subtract(discount).multiply(java.math.BigDecimal.valueOf(0.12));
    java.math.BigDecimal total = subtotal.subtract(discount).add(tax);

    // Create a temporary order for summary
    OrderDTO summaryOrder = new OrderDTO();
    summaryOrder.setBranchId(orderRequestDTO.getBranchId());
    summaryOrder.setClientName(orderRequestDTO.getClientName());
    summaryOrder.setClientEmail(orderRequestDTO.getClientEmail());
    summaryOrder.setClientPhone(orderRequestDTO.getClientPhone());
    summaryOrder.setPreferredDateTime(orderRequestDTO.getPreferredDateTime());
    summaryOrder.setPaymentMethod(orderRequestDTO.getPaymentMethod());
    summaryOrder.setDeliveryAddress(orderRequestDTO.getDeliveryAddress());
    summaryOrder.setNotes(orderRequestDTO.getNotes());
    summaryOrder.setSubtotal(subtotal);
    summaryOrder.setDiscount(discount);
    summaryOrder.setTax(tax);
    summaryOrder.setTotal(total);

    return ResponseEntity.ok(summaryOrder);
  }

  @PostMapping("/confirm")
  @Operation(summary = "Confirm order", description = "Confirm the order after checkout")
  public ResponseEntity<OrderDTO> confirmOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
    OrderDTO createdOrder = orderService.createOrder(orderRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
  }

  @GetMapping("/{orderId}")
  @Operation(summary = "Get order after confirmation", description = "Get order details after confirmation")
  public ResponseEntity<OrderDTO> getOrderAfterConfirmation(@PathVariable Long orderId) {
    OrderDTO order = orderService.getOrderById(orderId);
    return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
  }
}