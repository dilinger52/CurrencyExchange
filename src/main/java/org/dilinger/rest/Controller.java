package org.dilinger.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.dilinger.entity.Currency;
import org.dilinger.service.CurrencyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Api(tags = "Currency exchange")
public class Controller {
    @Autowired
    private CurrencyManager currencyManager;

    public Controller(CurrencyManager currencyManager) {
        this.currencyManager = currencyManager;
    }

    @GetMapping(path = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get a list of currencies exchange rate from different sources for today", response = Currency.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved currency exchange")

    })
    public @ResponseBody List<Currency> getTodayCurrencyExchange() {
        return currencyManager.getTodayCurrencyExchange();
    }


    @GetMapping(path = "/start/{startDay}/end/{endDay}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get a list of currencies exchange rate from different sources for chosen dates", response = Currency.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved currency exchange")

    })
    public @ResponseBody List<Currency> getPeriodCurrencyExchange(@PathVariable String startDay, @PathVariable String endDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        Date startDayF = new Date(LocalDate.parse(startDay, formatter).atTime(LocalTime.MIDNIGHT).toInstant(ZoneOffset.UTC).toEpochMilli());
        Date endDayF = new Date(LocalDate.parse(endDay, formatter).atTime(LocalTime.MIDNIGHT).toInstant(ZoneOffset.UTC).toEpochMilli());
        return currencyManager.getPeriodCurrencyExchange(startDayF, endDayF);
    }
}
