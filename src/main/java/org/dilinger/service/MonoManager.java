package org.dilinger.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dilinger.repository.CurrencyRepository;
import org.dilinger.entity.Currency;
import org.dilinger.entity.MonoCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MonoManager implements SourceManager {

    @Autowired
    private final CurrencyRepository currencyRepository;

    public MonoManager(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void getTodayCurrencyExchange() {
        String url = "https://api.monobank.ua/bank/currency";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Currency> currencys = new ArrayList<>();
        Date date = new Date(System.currentTimeMillis());
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            List<MonoCurrency> monoCurrencys = objectMapper.readValue(responseBody, new TypeReference<>(){});
            for (MonoCurrency monoCurrency : monoCurrencys) {
                if (Objects.equals(monoCurrency.getCurrencyCodeA(), "840")
                        || (Objects.equals(monoCurrency.getCurrencyCodeA(), "978") && !Objects.equals(monoCurrency.getCurrencyCodeB(), "840"))
                        || Objects.equals(monoCurrency.getCurrencyCodeA(), "985")) {
                    Currency currency = new Currency();
                    currency.setName(convertCode(monoCurrency.getCurrencyCodeA()));
                    currency.setDate(date);
                    currency.setSource(String.valueOf(Currency.Source.MONOBANK));
                    if (monoCurrency.getRateBuy() != 0 && monoCurrency.getRateSell() != 0) {
                        currency.setAverageExchange((monoCurrency.getRateBuy() + monoCurrency.getRateSell()) / 2);
                    } else {
                        currency.setAverageExchange(monoCurrency.getRateCross());
                    }
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


    String convertCode(String code) {
        return switch (code) {
            case "978" -> "EUR";
            case "985" -> "PLN";
            case "840" -> "USD";
            default -> null;
        };
    }
}
