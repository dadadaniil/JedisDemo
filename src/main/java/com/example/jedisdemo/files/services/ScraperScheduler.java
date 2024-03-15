package com.example.jedisdemo.files.services;

import com.example.jedisdemo.files.StockScraperService;
import com.example.jedisdemo.files.model.StockPrice;
import com.example.jedisdemo.files.repository.StockRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScraperScheduler {

    private final StockScraperService scraperService;
    private final StockRepository stockRepository;

    public ScraperScheduler(StockScraperService scraperService, StockRepository stockRepository) {
        this.scraperService = scraperService;
        this.stockRepository = stockRepository;
    }

    @Scheduled(fixedRate = 10000)
    public void runScraper() {
        String[] symbols = {"AAPL", "GOOGL", "MSFT", "TSLA"};

        for (String symbol : symbols) {
            StockPrice stockPrice = scraperService.scrapeStock(symbol);
            stockRepository.saveStock(stockPrice);
        }
    }
}
