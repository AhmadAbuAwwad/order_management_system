package com.project.ordermanagementsystems.controller;

import com.project.ordermanagementsystems.controller.dto.ProductOrderDTO;
import com.project.ordermanagementsystems.controller.request.ProductOrderCreateUpdateRequest;
import com.project.ordermanagementsystems.model.ProductOrder;
import com.project.ordermanagementsystems.service.ProductOrderService;
import com.project.ordermanagementsystems.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product-order")
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private Mapper mapper;


    @GetMapping
    public ResponseEntity<List<ProductOrderDTO>> getAllProductOrders() {
        List<ProductOrder> productOrder = productOrderService.getAllProductOrders();
        return new ResponseEntity<>(mapper.mapAsList(ProductOrderDTO.class, new ArrayList<>(productOrder)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOrderDTO> getProductOrderById(@PathVariable Long id) {
        ProductOrder productOrder = productOrderService.getProductOrderById(id);
        if (productOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((ProductOrderDTO) mapper.map(ProductOrderDTO.class, productOrder), HttpStatus.OK);

    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductOrderDTO>> getProductOrderByProductId(@PathVariable Long productId) {
        List<ProductOrder> productOrders = productOrderService.getProductOrderByProductId(productId);
        if (productOrders == null && productOrders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.mapAsList(ProductOrderDTO.class, new ArrayList<>(productOrders)), HttpStatus.OK);

    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ProductOrderDTO>> getProductOrdersByOrderId(@PathVariable Long orderId) {
        List<ProductOrder> productOrders = productOrderService.getProductOrderByOrderId(orderId);
        if (productOrders == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.mapAsList(ProductOrderDTO.class, new ArrayList<>(productOrders)), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductOrder(@PathVariable Long id) {
        ProductOrder productOrder = productOrderService.getProductOrderById(id);
        if (productOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productOrderService.deleteProductOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
