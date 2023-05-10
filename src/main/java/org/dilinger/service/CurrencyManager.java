package org.dilinger.service;

import org.dilinger.repository.CurrencyRepository;
import org.dilinger.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
public class CurrencyManager {

    @Autowired
    private final CurrencyRepository currencyRepository;

    public CurrencyManager(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getTodayCurrencyExchange() {
        Date today = new Date(System.currentTimeMillis());
        return currencyRepository.findByDate(today);
    }

    public List<Currency> getPeriodCurrencyExchange(Date startDate, Date endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        String startDateF = startDate.toLocalDate().format(formatter);
        String endDateF = endDate.toLocalDate().format(formatter);
        return currencyRepository.findByPeriod(startDateF, endDateF);
    }

    public void addCurrencyExchange(Currency currency) {
        currencyRepository.save(currency);
    }
}
