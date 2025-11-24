package com.ramen.ordering.repository;

import com.ramen.ordering.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByClientName(String clientName);

  List<Order> findByBranchId(Long branchId);

  @Query("SELECT o FROM Order o WHERE o.clientEmail = :clientEmail")
  List<Order> findByClientEmail(@Param("clientEmail") String clientEmail);

  @Query("SELECT o FROM Order o WHERE o.clientPhone = :clientPhone")
  List<Order> findByClientPhone(@Param("clientPhone") String clientPhone);
}