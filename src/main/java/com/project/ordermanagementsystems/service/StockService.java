package com.project.ordermanagementsystems.service;


import com.project.ordermanagementsystems.controller.request.StockCreateUpdateRequest;
import com.project.ordermanagementsystems.exception.ResourceNotFoundException;
import com.project.ordermanagementsystems.model.Product;
import com.project.ordermanagementsystems.model.Stock;
import com.project.ordermanagementsystems.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductService productService;

    public Stock createStock(StockCreateUpdateRequest stockCreateUpdateRequest) {

        Product product = productService.getProductById(stockCreateUpdateRequest.getProductId());
        if (product == null) {
            throw new ResourceNotFoundException("No product with id: " + stockCreateUpdateRequest.getProductId());
        }

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(stockCreateUpdateRequest.getQuantity());
        stock.setUpdatedAt(stockCreateUpdateRequest.getUpdatedAt());
        return stockRepository.save(stock);
    }

    public List<Stock> getAllStock() {
        return stockRepository.findAll();
    }


    public Stock getStockById(Long id) {
        Optional<Stock> stockOptional = stockRepository.findById(id);
        return stockOptional.orElse(null);
    }

    public Stock updateStock(Long id, StockCreateUpdateRequest updatedStockDTO) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (stock == null) {
            throw new ResourceNotFoundException("No Stock with id: " + id);
        }

        Product product = productService.getProductById(updatedStockDTO.getProductId());
        if (product == null) {
            throw new ResourceNotFoundException("No product with id: " + id);
        }

        stock.setProduct(product);
        stock.setQuantity(updatedStockDTO.getQuantity());
        stock.setUpdatedAt(updatedStockDTO.getUpdatedAt());
        return stockRepository.save(stock);
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    public Stock  updateStockByProductId(Long productId, Integer quantity) {
        List<Stock> stocks = stockRepository.findStockByProductId(productId);
        if (stocks == null || stocks.isEmpty()) {
            throw new ResourceNotFoundException("No Stock with product id: " + productId);
        }

        Stock stock = null;
        for (Stock stockFound : stocks) {
            if (stockFound.getQuantity() > quantity) {
                stock = stockFound;
                break;
            }
        }

        if(stock == null){
            throw new ResourceNotFoundException("No Product Left for product id: " + productId);
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("No product with id: " + productId);
        }

        stock.setProduct(product);
        stock.setQuantity(stock.getQuantity() - quantity);
        stock.setUpdatedAt(new Date());
        return stockRepository.save(stock);
    }

}
