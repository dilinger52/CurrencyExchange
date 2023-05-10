package org.dilinger.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonoCurrency {
    String currencyCodeA;
    String currencyCodeB;
    Date date;
    double rateBuy;
    double rateCross;
    double rateSell;

    public String getCurrencyCodeA() {
        return currencyCodeA;
    }

    public void setCurrencyCodeA(String currencyCodeA) {
        this.currencyCodeA = currencyCodeA;
    }

    public String getCurrencyCodeB() {
        return currencyCodeB;
    }

    public void setCurrencyCodeB(String currencyCodeB) {
        this.currencyCodeB = currencyCodeB;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getRateBuy() {
        return rateBuy;
    }

    public void setRateBuy(double rateBuy) {
        this.rateBuy = rateBuy;
    }

    public double getRateCross() {
        return rateCross;
    }

    public void setRateCross(double rateCross) {
        this.rateCross = rateCross;
    }

    public double getRateSell() {
        return rateSell;
    }

    public void setRateSell(double rateSell) {
        this.rateSell = rateSell;
    }
}
