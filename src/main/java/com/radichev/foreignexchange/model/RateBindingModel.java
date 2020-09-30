package com.radichev.foreignexchange.model;

public class RateBindingModel {
    private String currencyFrom;
    private String currencyTo;

    public RateBindingModel() {
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }
}
