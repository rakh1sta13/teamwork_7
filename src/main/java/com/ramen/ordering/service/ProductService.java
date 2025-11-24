package com.ramen.ordering.service;

import com.ramen.ordering.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getAvailableProducts();
    List<ProductDTO> getProductsByBranch(Long branchId);
    List<ProductDTO> getProductsByCategory(Long categoryId);
    List<ProductDTO> getProductsByBranchAndCategory(Long branchId, Long categoryId);
    ProductDTO getProductById(Long id);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    boolean deleteProduct(Long id);
}