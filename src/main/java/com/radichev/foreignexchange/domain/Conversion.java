package com.radichev.foreignexchange.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "conversion")
public class Conversion {
    private String id;
    private String currencyFrom;
    private String currencyTo;
    private BigDecimal exchangeRate;
    private BigDecimal amountBefore;
    private BigDecimal amountAfter;
    private LocalDate conversionDate;

    public Conversion() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column
    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    @Column
    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    @Column
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Column
    public BigDecimal getAmountBefore() {
        return amountBefore;
    }

    public void setAmountBefore(BigDecimal amountBefore) {
        this.amountBefore = amountBefore;
    }

    @Column
    public BigDecimal getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(BigDecimal amountAfter) {
        this.amountAfter = amountAfter;
    }

    @Column
    public LocalDate getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(LocalDate conversionDate) {
        this.conversionDate = conversionDate;
    }
}
