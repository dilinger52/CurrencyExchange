package org.dilinger.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dilinger.entity.ExchangeRate;
import org.dilinger.repository.CurrencyRepository;
import org.dilinger.entity.Currency;
import org.dilinger.entity.PrivateCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PrivateManager implements SourceManager {
    @Autowired
    private final CurrencyRepository currencyRepository;

    public PrivateManager(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void getTodayCurrencyExchange() {
        Date date = new Date(System.currentTimeMillis());
        String dateF = date.toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        System.out.println(dateF);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Currency> currencys = new ArrayList<>();
        String url = "https://api.privatbank.ua/p24api/exchange_rates?json&date=" + dateF;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();
                PrivateCurrency privateCurrency = objectMapper.readValue(responseBody, new TypeReference<>(){});
                System.out.println(privateCurrency);
                List<ExchangeRate> exchangeRates = privateCurrency.getExchangeRate();
                for (ExchangeRate exchangeRate :
                        exchangeRates) {
                    if (Objects.equals(exchangeRate.getCurrency(), "EUR") || Objects.equals(exchangeRate.getCurrency(), "USD") || Objects.equals(exchangeRate.getCurrency(), "PLN")) {
                        Currency currency = new Currency();
                        currency.setName(exchangeRate.getCurrency());
                        currency.setDate(date);
                        currency.setSource(String.valueOf(Currency.Source.PRIVATEBANK));
                        currency.setAverageExchange((exchangeRate.getSaleRate() + exchangeRate.getPurchaseRate()) / 2);
                        currencys.add(currency);
                    }
                }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        currencyRepository.saveAll(currencys);
    }

    @Override
    public void getPeriodCurrencyExchange() {

    }
}
