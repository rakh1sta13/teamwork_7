package com.ramen.ordering.service;

import com.ramen.ordering.dto.OrderDTO;
import com.ramen.ordering.dto.OrderItemRequestDTO;
import com.ramen.ordering.dto.OrderRequestDTO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
  List<OrderDTO> getAllOrders();
  OrderDTO getOrderById(Long id);
  List<OrderDTO> getOrdersByClientName(String clientName);
  List<OrderDTO> getOrdersByClientEmail(String clientEmail);
  List<OrderDTO> getOrdersByClientPhone(String clientPhone);
  OrderDTO getOrderByPhoneAndId(String clientPhone, Long orderId);
  List<OrderDTO> getOrdersByBranch(Long branchId);
  OrderDTO createOrder(OrderRequestDTO orderRequestDTO);
  OrderDTO updateOrder(Long id, OrderDTO orderDTO);
  boolean deleteOrder(Long id);
  java.util.Map.Entry<BigDecimal, BigDecimal> calculateOrderTotal(List<OrderItemRequestDTO> orderItems); // Returns (subtotal, discount)
}