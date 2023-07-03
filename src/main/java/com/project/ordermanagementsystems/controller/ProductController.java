package com.project.ordermanagementsystems.controller;

import com.project.ordermanagementsystems.controller.dto.ProductDTO;
import com.project.ordermanagementsystems.model.Product;
import com.project.ordermanagementsystems.service.ProductService;
import com.project.ordermanagementsystems.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private Mapper mapper;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO ProductDTO) {
        Product product = productService.createProduct(mapper.map(Product.class, ProductDTO));
        return new ResponseEntity<>((ProductDTO) mapper.map(ProductDTO.class, product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProduct();
        return new ResponseEntity<>(mapper.mapAsList(ProductDTO.class, new ArrayList<>(products)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((ProductDTO) mapper.map(ProductDTO.class, product), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO updatedSpecialityDTO) {
        Product product = productService.updateProduct(id, updatedSpecialityDTO);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((ProductDTO) mapper.map(ProductDTO.class, product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
