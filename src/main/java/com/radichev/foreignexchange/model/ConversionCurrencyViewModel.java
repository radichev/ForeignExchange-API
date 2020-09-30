package com.radichev.foreignexchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ConversionCurrencyViewModel {
    private BigDecimal amountAfter;
    private String id;

    public ConversionCurrencyViewModel() {
    }

    @JsonProperty("amount")
    public BigDecimal getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(BigDecimal amountAfter) {
        this.amountAfter = amountAfter;
    }

    @JsonProperty("transactionId")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
