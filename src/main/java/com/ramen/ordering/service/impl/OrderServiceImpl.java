package com.ramen.ordering.service.impl;

import com.ramen.ordering.dto.OrderDTO;
import com.ramen.ordering.dto.OrderItemDTO;
import com.ramen.ordering.dto.OrderItemRequestDTO;
import com.ramen.ordering.dto.OrderRequestDTO;
import com.ramen.ordering.entity.*;
import com.ramen.ordering.mapper.OrderItemMapper;
import com.ramen.ordering.mapper.OrderMapper;
import com.ramen.ordering.repository.*;
import com.ramen.ordering.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final BranchRepository branchRepository;
  private final ProductRepository productRepository;
  private final OrderMapper orderMapper;
  private final OrderItemMapper orderItemMapper;

  @Override
  public List<OrderDTO> getAllOrders() {
    return orderRepository.findAll()
        .stream()
        .map(order -> {
          OrderDTO orderDTO = orderMapper.toDTO(order);
          // Load and set order items with product names
          List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
          List<OrderItemDTO> orderItemDTOs = orderItems.stream()
              .map(orderItem -> {
                OrderItemDTO itemDTO = orderItemMapper.toDTO(orderItem);
                // Set product name manually since we don't have relations
                Product product = productRepository.findById(orderItem.getProductId())
                    .map(productEntity -> {
                      itemDTO.setProductName(productEntity.getName());
                      return productEntity;
                    })
                    .orElse(null);
                return itemDTO;
              })
              .collect(Collectors.toList());
          orderDTO.setOrderItems(orderItemDTOs);
          return orderDTO;
        })
        .collect(Collectors.toList());
  }

  @Override
  public OrderDTO getOrderById(Long id) {
    return orderRepository.findById(id)
        .map(order -> {
          OrderDTO orderDTO = orderMapper.toDTO(order);
          // Load and set order items with product names
          List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
          List<OrderItemDTO> orderItemDTOs = orderItems.stream()
              .map(orderItem -> {
                OrderItemDTO itemDTO = orderItemMapper.toDTO(orderItem);
                // Set product name manually since we don't have relations
                Product product = productRepository.findById(orderItem.getProductId())
                    .map(productEntity -> {
                      itemDTO.setProductName(productEntity.getName());
                      return productEntity;
                    })
                    .orElse(null);
                return itemDTO;
              })
              .collect(Collectors.toList());
          orderDTO.setOrderItems(orderItemDTOs);
          return orderDTO;
        })
        .orElse(null);
  }

  @Override
  public List<OrderDTO> getOrdersByClientName(String clientName) {
    return orderRepository.findByClientName(clientName)
        .stream()
        .map(order -> {
          OrderDTO orderDTO = orderMapper.toDTO(order);
          // Load and set order items with product names
          List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
          List<OrderItemDTO> orderItemDTOs = orderItems.stream()
              .map(orderItem -> {
                OrderItemDTO itemDTO = orderItemMapper.toDTO(orderItem);
                // Set product name manually since we don't have relations
                Product product = productRepository.findById(orderItem.getProductId())
                    .map(productEntity -> {
                      itemDTO.setProductName(productEntity.getName());
                      return productEntity;
                    })
                    .orElse(null);
                return itemDTO;
              })
              .collect(Collectors.toList());
          orderDTO.setOrderItems(orderItemDTOs);
          return orderDTO;
        })
        .collect(Collectors.toList());
  }

  @Override
  public List<OrderDTO> getOrdersByClientEmail(String clientEmail) {
    return orderRepository.findByClientEmail(clientEmail)
        .stream()
        .map(order -> {
          OrderDTO orderDTO = orderMapper.toDTO(order);
          // Load and set order items with product names
          List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
          List<OrderItemDTO> orderItemDTOs = orderItems.stream()
              .map(orderItem -> {
                OrderItemDTO itemDTO = orderItemMapper.toDTO(orderItem);
                // Set product name manually since we don't have relations
                Product product = productRepository.findById(orderItem.getProductId())
                    .map(productEntity -> {
                      itemDTO.setProductName(productEntity.getName());
                      return productEntity;
                    })
                    .orElse(null);
                return itemDTO;
              })
              .collect(Collectors.toList());
          orderDTO.setOrderItems(orderItemDTOs);
          return orderDTO;
        })
        .collect(Collectors.toList());
  }

  @Override
  public List<OrderDTO> getOrdersByClientPhone(String clientPhone) {
    return orderRepository.findByClientPhone(clientPhone)
        .stream()
        .map(order -> {
          OrderDTO orderDTO = orderMapper.toDTO(order);
          // Load and set order items with product names
          List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
          List<OrderItemDTO> orderItemDTOs = orderItems.stream()
              .map(orderItem -> {
                OrderItemDTO itemDTO = orderItemMapper.toDTO(orderItem);
                // Set product name manually since we don't have relations
                Product product = productRepository.findById(orderItem.getProductId())
                    .map(productEntity -> {
                      itemDTO.setProductName(productEntity.getName());
                      return productEntity;
                    })
                    .orElse(null);
                return itemDTO;
              })
              .collect(Collectors.toList());
          orderDTO.setOrderItems(orderItemDTOs);
          return orderDTO;
        })
        .collect(Collectors.toList());
  }

  @Override
  public OrderDTO getOrderByPhoneAndId(String clientPhone, Long orderId) {
    Order order = orderRepository.findById(orderId).orElse(null);
    if (order != null && order.getClientPhone().equals(clientPhone)) {
      OrderDTO orderDTO = orderMapper.toDTO(order);
      // Load and set order items with product names
      List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
      List<OrderItemDTO> orderItemDTOs = orderItems.stream()
          .map(orderItem -> {
            OrderItemDTO itemDTO = orderItemMapper.toDTO(orderItem);
            // Set product name manually since we don't have relations
            Product product = productRepository.findById(orderItem.getProductId())
                .map(productEntity -> {
                  itemDTO.setProductName(productEntity.getName());
                  return productEntity;
                })
                .orElse(null);
            return itemDTO;
          })
          .collect(Collectors.toList());
      orderDTO.setOrderItems(orderItemDTOs);
      return orderDTO;
    }
    return null; // Order not found or doesn't belong to the client
  }

  @Override
  public List<OrderDTO> getOrdersByBranch(Long branchId) {
    return orderRepository.findByBranchId(branchId)
        .stream()
        .map(order -> {
          OrderDTO orderDTO = orderMapper.toDTO(order);
          // Load and set order items with product names
          List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
          List<OrderItemDTO> orderItemDTOs = orderItems.stream()
              .map(orderItem -> {
                OrderItemDTO itemDTO = orderItemMapper.toDTO(orderItem);
                // Set product name manually since we don't have relations
                Product product = productRepository.findById(orderItem.getProductId())
                    .map(productEntity -> {
                      itemDTO.setProductName(productEntity.getName());
                      return productEntity;
                    })
                    .orElse(null);
                return itemDTO;
              })
              .collect(Collectors.toList());
          orderDTO.setOrderItems(orderItemDTOs);
          return orderDTO;
        })
        .collect(Collectors.toList());
  }

  @Override
  public OrderDTO createOrder(OrderRequestDTO orderRequestDTO) {
    // Validate branch exists
    Branch branch = branchRepository.findById(orderRequestDTO.getBranchId())
        .orElseThrow(() -> new RuntimeException("Branch not found with id: " + orderRequestDTO.getBranchId()));

    // Validate products exist and calculate totals
    List<OrderItem> orderItems = orderRequestDTO.getOrderItems().stream()
        .map(itemRequest -> {
          Product product = productRepository.findById(itemRequest.getProductId())
              .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.getProductId()));

          // Calculate item total
          BigDecimal unitPrice = product.getPrice();
          BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

          OrderItem orderItem = new OrderItem();
          orderItem.setOrderId(null); // Will be set after order is saved
          orderItem.setProductId(itemRequest.getProductId());
          orderItem.setQuantity(itemRequest.getQuantity());
          orderItem.setUnitPrice(unitPrice);
          orderItem.setTotalPrice(itemTotal);

          return orderItem;
        })
        .collect(Collectors.toList());

    // Calculate totals
    Map.Entry<BigDecimal, BigDecimal> totalCalculation = calculateOrderTotal(orderRequestDTO.getOrderItems());
    BigDecimal subtotal = totalCalculation.getKey();
    BigDecimal discount = totalCalculation.getValue();

    // Calculate tax (12% VAT)
    BigDecimal tax = subtotal.subtract(discount).multiply(BigDecimal.valueOf(0.12)).setScale(2, RoundingMode.HALF_UP);
    BigDecimal total = subtotal.subtract(discount).add(tax);

    // Create order
    Order orderEntity = new Order();
    orderEntity.setBranch(branch);
    orderEntity.setClientName(orderRequestDTO.getClientName());
    orderEntity.setClientEmail(orderRequestDTO.getClientEmail()); // Optional, phone number is primary contact
    orderEntity.setClientPhone(orderRequestDTO.getClientPhone()); // Primary contact method
    orderEntity.setPreferredDateTime(orderRequestDTO.getPreferredDateTime());
    orderEntity.setPaymentMethod(orderRequestDTO.getPaymentMethod());
    orderEntity.setDeliveryAddress(orderRequestDTO.getDeliveryAddress());
    orderEntity.setNotes(orderRequestDTO.getNotes());
    orderEntity.setSubtotal(subtotal);
    orderEntity.setDiscount(discount);
    orderEntity.setTax(tax);
    orderEntity.setTotal(total);

    Order savedOrder = orderRepository.save(orderEntity);

    // Set the order ID for each item and save them
    for (OrderItem orderItem : orderItems) {
      orderItem.setOrderId(savedOrder.getId());
    }
    List<OrderItem> savedOrderItems = orderItemRepository.saveAll(orderItems);

    // Map to DTO and return
    OrderDTO orderDTO = orderMapper.toDTO(savedOrder);
    List<OrderItemDTO> orderItemDTOs = savedOrderItems.stream()
        .map(orderItem -> {
          OrderItemDTO dto = orderItemMapper.toDTO(orderItem);
          // Set product name manually since we don't have relations
          Product product = productRepository.findById(orderItem.getProductId())
              .map(productEntity -> {
                dto.setProductName(productEntity.getName());
                return productEntity;
              })
              .orElse(null);
          return dto;
        })
        .collect(Collectors.toList());
    return OrderDTO.builder()
        .id(orderDTO.getId())
        .branchId(orderDTO.getBranchId())
        .branchName(orderDTO.getBranchName())
        .clientName(orderDTO.getClientName())
        .clientEmail(orderDTO.getClientEmail())
        .clientPhone(orderDTO.getClientPhone())
        .preferredDateTime(orderDTO.getPreferredDateTime())
        .paymentMethod(orderDTO.getPaymentMethod())
        .status(orderDTO.getStatus())
        .subtotal(orderDTO.getSubtotal())
        .discount(orderDTO.getDiscount())
        .tax(orderDTO.getTax())
        .total(orderDTO.getTotal())
        .deliveryAddress(orderDTO.getDeliveryAddress())
        .notes(orderDTO.getNotes())
        .orderItems(orderItemDTOs)
        .createdAt(orderDTO.getCreatedAt())
        .updatedAt(orderDTO.getUpdatedAt())
        .build();
  }

  @Override
  public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
    Order existingOrder = orderRepository.findById(id).orElse(null);
    if (existingOrder == null) {
      return null;
    }

    // Validate branch exists
    Branch branch = branchRepository.findById(orderDTO.getBranchId())
        .orElseThrow(() -> new RuntimeException("Branch not found with id: " + orderDTO.getBranchId()));

    orderMapper.updateEntityFromDTO(orderDTO, existingOrder);
    existingOrder.setBranch(branch);
    Order updatedOrder = orderRepository.save(existingOrder);

    // Return the updated order with order items
    OrderDTO resultDTO = orderMapper.toDTO(updatedOrder);
    List<OrderItem> orderItems = orderItemRepository.findByOrderId(updatedOrder.getId());
    List<OrderItemDTO> orderItemDTOs = orderItems.stream()
        .map(orderItem -> {
          OrderItemDTO itemDTO = orderItemMapper.toDTO(orderItem);
          // Set product name manually since we don't have relations
          Product product = productRepository.findById(orderItem.getProductId())
              .map(productEntity -> {
                itemDTO.setProductName(productEntity.getName());
                return productEntity;
              })
              .orElse(null);
          return itemDTO;
        })
        .collect(Collectors.toList());
    resultDTO.setOrderItems(orderItemDTOs);
    return resultDTO;
  }

  @Override
  public boolean deleteOrder(Long id) {
    if (!orderRepository.existsById(id)) {
      return false;
    }
    orderRepository.deleteById(id);
    return true;
  }

  @Override
  public Map.Entry<BigDecimal, BigDecimal> calculateOrderTotal(List<OrderItemRequestDTO> orderItems) {
    BigDecimal subtotal = BigDecimal.ZERO;
    BigDecimal discount = BigDecimal.ZERO;

    // Calculate subtotal
    for (OrderItemRequestDTO item : orderItems) {
      Product product = productRepository.findById(item.getProductId())
          .orElseThrow(() -> new RuntimeException("Product not found with id: " + item.getProductId()));

      BigDecimal itemSubtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
      subtotal = subtotal.add(itemSubtotal);

      // Apply 5% discount if 3 or more of same product
      if (item.getQuantity() >= 3) {
        BigDecimal itemDiscount = itemSubtotal.multiply(BigDecimal.valueOf(0.05));
        discount = discount.add(itemDiscount);
      }
    }

    // Apply 10% discount if subtotal > 300,000
    if (subtotal.compareTo(BigDecimal.valueOf(300000)) > 0) {
      BigDecimal additionalDiscount = subtotal.multiply(BigDecimal.valueOf(0.10));
      discount = discount.add(additionalDiscount);
    }

    return Map.entry(subtotal, discount);
  }
}