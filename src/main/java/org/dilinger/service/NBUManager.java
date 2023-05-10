package org.dilinger.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dilinger.repository.CurrencyRepository;
import org.dilinger.entity.Currency;
import org.dilinger.entity.NBUCurrency;
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
public class NBUManager implements SourceManager {

    @Autowired
    private final CurrencyRepository currencyRepository;

    public NBUManager(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
    @Override
    public void getTodayCurrencyExchange() {
        String url = "https://bank.gov.ua/NBU_Exchange/exchange?json";
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
            List<NBUCurrency> nbuCurrencies = objectMapper.readValue(responseBody, new TypeReference<>(){});
            for (NBUCurrency nbuCurrency : nbuCurrencies) {
                if (Objects.equals(nbuCurrency.getCurrencyCodeL(), "USD") || Objects.equals(nbuCurrency.getCurrencyCodeL(), "EUR") || Objects.equals(nbuCurrency.getCurrencyCodeL(), "PLN")) {
                    Currency currency = new Currency();
                    currency.setName(nbuCurrency.getCurrencyCodeL());
                    currency.setDate(date);
                    currency.setSource(String.valueOf(Currency.Source.NBU));
                    currency.setAverageExchange(nbuCurrency.getAmount());
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
