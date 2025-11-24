package com.example.foodordering;

import com.example.foodordering.entity.Branch;
import com.example.foodordering.entity.Product;
import com.example.foodordering.entity.Order;
import com.example.foodordering.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FoodOrderingApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring context loads correctly
        assertTrue(true);
    }

    @Test
    void testEntityCreation() {
        // Test creating entities
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setName("Main Branch");
        branch.setAddress("123 Main St");
        branch.setPhone("555-1234");
        branch.setEmail("main@example.com");
        branch.setCreatedAt(LocalDateTime.now());

        Product product = new Product();
        product.setId(1L);
        product.setName("Pizza");
        product.setDescription("Delicious pizza");
        product.setPrice(new BigDecimal("15.99"));
        product.setBranch(branch);
        product.setAvailable(true);

        Order order = new Order();
        order.setId(1L);
        order.setBranch(branch);
        order.setCustomerName("John Doe");
        order.setCustomerPhone("555-5678");
        order.setCustomerEmail("john@example.com");
        order.setOrderType(Order.OrderType.DELIVERY);
        order.setStatus(Order.OrderStatus.PENDING);
        order.setTotalAmount(new BigDecimal("31.98"));
        order.setCreatedAt(LocalDateTime.now());

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItem.setUnitPrice(new BigDecimal("15.99"));
        orderItem.setTotalPrice(new BigDecimal("31.98"));

        // Verify entities are created correctly
        assertNotNull(branch);
        assertNotNull(product);
        assertNotNull(order);
        assertNotNull(orderItem);

        assertEquals("Main Branch", branch.getName());
        assertEquals("Pizza", product.getName());
        assertEquals(Order.OrderType.DELIVERY, order.getOrderType());
        assertEquals(2, orderItem.getQuantity());
    }
}