package com.example.jedisdemo.files.controller;

import com.example.jedisdemo.files.model.StockPrice;
import com.example.jedisdemo.files.repository.StockRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockRepository stockRepository;

    public StockController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping("/{symbol}")
    public StockPrice getStock(@PathVariable String symbol) {
        return stockRepository.findStock(symbol);
    }
}
