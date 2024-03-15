package com.example.jedisdemo.files.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockPrice {
    private String symbol;
    private BigDecimal price;
    private BigDecimal change;
    private BigDecimal percentChange;


    public StockPrice(String symbol, BigDecimal price, BigDecimal change, BigDecimal percentChange) {
        this.symbol = symbol;
        this.price = price;
        this.change = change;
        this.percentChange = percentChange;
    }
}
