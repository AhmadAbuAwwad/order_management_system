package com.project.ordermanagementsystems.repository;

import com.project.ordermanagementsystems.model.Order;
import com.project.ordermanagementsystems.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findStockByProductId(Long productId);
}
