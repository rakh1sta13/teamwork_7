package com.ramen.ordering.controller;

import com.ramen.ordering.dto.OrderDTO;
import com.ramen.ordering.dto.OrderRequestDTO;
import com.ramen.ordering.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "Endpoints for managing client orders")
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  @Operation(summary = "Get all orders", description = "Retrieve a list of all orders")
  public ResponseEntity<List<OrderDTO>> getAllOrders() {
    List<OrderDTO> orders = orderService.getAllOrders();
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get order by ID", description = "Retrieve a specific order by its ID")
  public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") Long id) {
    OrderDTO order = orderService.getOrderById(id);
    return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
  }

  @GetMapping("/client/{clientName}")
  @Operation(summary = "Get orders by client name", description = "Retrieve orders for a specific client by name")
  public ResponseEntity<List<OrderDTO>> getOrdersByClientName(@PathVariable String clientName) {
    List<OrderDTO> orders = orderService.getOrdersByClientName(clientName);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/client-email/{clientEmail}")
  @Operation(summary = "Get orders by client email", description = "Retrieve orders for a client by their email (deprecated, use phone-based lookup instead)")
  public ResponseEntity<List<OrderDTO>> getOrdersByClientEmail(@PathVariable String clientEmail) {
    List<OrderDTO> orders = orderService.getOrdersByClientEmail(clientEmail);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/client-phone/{clientPhone}")
  @Operation(summary = "Get orders by client phone", description = "Retrieve orders for a client by their phone number")
  public ResponseEntity<List<OrderDTO>> getOrdersByClientPhone(@PathVariable String clientPhone) {
    List<OrderDTO> orders = orderService.getOrdersByClientPhone(clientPhone);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/phone/{clientPhone}/order/{orderId}")
  @Operation(summary = "Get specific order by client phone and order ID", description = "Retrieve a specific order by client phone number and order ID")
  public ResponseEntity<OrderDTO> getOrderByPhoneAndId(@PathVariable String clientPhone, @PathVariable Long orderId) {
    OrderDTO order = orderService.getOrderByPhoneAndId(clientPhone, orderId);
    return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
  }

  @GetMapping("/branch/{branchId}")
  @Operation(summary = "Get orders by branch ID", description = "Retrieve orders for a specific branch")
  public ResponseEntity<List<OrderDTO>> getOrdersByBranch(@PathVariable Long branchId) {
    List<OrderDTO> orders = orderService.getOrdersByBranch(branchId);
    return ResponseEntity.ok(orders);
  }

  @PostMapping
  @Operation(summary = "Create a new order", description = "Create a new client order")
  public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
    OrderDTO createdOrder = orderService.createOrder(orderRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an order", description = "Update an existing order by its ID")
  public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
    OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
    return updatedOrder != null ? ResponseEntity.ok(updatedOrder) : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an order", description = "Delete an order by its ID")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    boolean deleted = orderService.deleteOrder(id);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}