package com.radichev.foreignexchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ConversionViewModel {
    private BigDecimal amountAfter;
    private String id;

    public ConversionViewModel() {
    }

    @JsonProperty("amount")
    public BigDecimal getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(BigDecimal amountAfter) {
        this.amountAfter = amountAfter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
