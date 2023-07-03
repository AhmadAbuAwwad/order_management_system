package com.project.ordermanagementsystems.controller;

import com.project.ordermanagementsystems.controller.dto.StockDTO;
import com.project.ordermanagementsystems.controller.request.StockCreateUpdateRequest;
import com.project.ordermanagementsystems.model.Stock;
import com.project.ordermanagementsystems.service.StockService;
import com.project.ordermanagementsystems.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private Mapper mapper;

    @PostMapping
    public ResponseEntity<StockDTO> createStock(@RequestBody StockCreateUpdateRequest stock) {
        Stock savedStock = stockService.createStock(stock);
        return new ResponseEntity<>((StockDTO) mapper.map(StockDTO.class, savedStock), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStock();
        return new ResponseEntity<>(mapper.mapAsList(StockDTO.class, new ArrayList<>(stocks)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        if (stock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((StockDTO) mapper.map(StockDTO.class, stock), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockDTO> updateStock(@PathVariable Long id, @RequestBody StockCreateUpdateRequest updatedStockDTO) {
        Stock savedStock = stockService.updateStock(id, updatedStockDTO);
        if (savedStock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((StockDTO) mapper.map(StockDTO.class, savedStock), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
