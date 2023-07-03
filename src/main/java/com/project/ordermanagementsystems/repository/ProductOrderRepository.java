package com.project.ordermanagementsystems.repository;

import com.project.ordermanagementsystems.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    List<ProductOrder> findProductOrderByProductId(Long productId);
    List<ProductOrder> findProductOrderByOrderId(Long orderId);
}
