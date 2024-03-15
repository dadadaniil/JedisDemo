package com.example.jedisdemo.files;

import com.example.jedisdemo.files.model.StockPrice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class StockScraperService {

    public StockPrice scrapeStock(String symbol) {
        String url = "https://finance.yahoo.com/quote/" + symbol;

        try {
            Document doc = Jsoup.connect(url).get();

            String priceText = doc.selectFirst("#quote-header-info fin-streamer[data-field=regularMarketPrice]").text();
            String changeText = doc.selectFirst("#quote-header-info fin-streamer[data-field=regularMarketChange]").text();
            String percentChangeText = doc.selectFirst("#quote-header-info fin-streamer[data-field=regularMarketChangePercent]").text().replace("(", "").replace(")", "").replace("%", "");

            BigDecimal price = new BigDecimal(priceText);
            BigDecimal change = new BigDecimal(changeText);
            BigDecimal percentChange = new BigDecimal(percentChangeText);

            return new StockPrice(symbol, price, change, percentChange);
        } catch (IOException e) {
            throw new RuntimeException("Failed to scrape stock: " + symbol, e);
        }
    }
}
