package com.project.ordermanagementsystems.service;


import com.project.ordermanagementsystems.controller.dto.ProductDTO;
import com.project.ordermanagementsystems.exception.ResourceNotFoundException;
import com.project.ordermanagementsystems.model.Product;
import com.project.ordermanagementsystems.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }


    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(!productOptional.isPresent()){
            throw new ResourceNotFoundException("No product with id: " + id);
        }
        return productOptional.get();
    }

    public Product updateProduct(Long id, ProductDTO updatedProductDTO) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ResourceNotFoundException("No product with id: " + id);
        }
        product.setName(updatedProductDTO.getName());
        product.setSlug(updatedProductDTO.getSlug());
        product.setReference(updatedProductDTO.getReference());
        product.setPrice(updatedProductDTO.getPrice());
        product.setStockable(updatedProductDTO.getStockable());
        product.setVat(updatedProductDTO.getVat());
        return productRepository.save(product);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByIds(List<Long> productsIds) {
        return productRepository.findAllById(productsIds);
    }
}
