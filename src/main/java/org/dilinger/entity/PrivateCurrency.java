package org.dilinger.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrivateCurrency {
    List<ExchangeRate> exchangeRate;

    public List<ExchangeRate> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(List<ExchangeRate> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "PrivateCurrency{" +
                "exchangeRate=" + exchangeRate +
                '}';
    }
}
