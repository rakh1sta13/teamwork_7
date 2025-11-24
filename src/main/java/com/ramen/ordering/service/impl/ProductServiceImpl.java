package com.ramen.ordering.service.impl;

import com.ramen.ordering.dto.ProductDTO;
import com.ramen.ordering.entity.Product;
import com.ramen.ordering.mapper.ProductMapper;
import com.ramen.ordering.repository.BranchRepository;
import com.ramen.ordering.repository.CategoryRepository;
import com.ramen.ordering.repository.ProductRepository;
import com.ramen.ordering.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAvailableProducts() {
        return productRepository.findAll()
                .stream()
                .filter(Product::getIsAvailable)
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByBranch(Long branchId) {
        return productRepository.findByBranchIdAndIsAvailableTrue(branchId)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndIsAvailableTrue(categoryId)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByBranchAndCategory(Long branchId, Long categoryId) {
        return productRepository.findByBranchIdAndCategoryIdAndIsAvailableTrue(branchId, categoryId)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO)
                .orElse(null);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        // Validate branch and category exist
        branchRepository.findById(productDTO.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + productDTO.getBranchId()));
        categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDTO.getCategoryId()));

        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return null;
        }

        // Validate branch and category exist
        branchRepository.findById(productDTO.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + productDTO.getBranchId()));
        categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDTO.getCategoryId()));

        productMapper.updateEntityFromDTO(productDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }
}