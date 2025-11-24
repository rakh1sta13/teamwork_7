package com.ramen.ordering.repository;

import com.ramen.ordering.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBranchIdAndIsAvailableTrue(Long branchId);
    
    List<Product> findByCategoryIdAndIsAvailableTrue(Long categoryId);
    
    @Query("SELECT p FROM Product p WHERE p.branch.id = :branchId AND p.category.id = :categoryId AND p.isAvailable = true")
    List<Product> findByBranchIdAndCategoryIdAndIsAvailableTrue(@Param("branchId") Long branchId, @Param("categoryId") Long categoryId);
}