package com.radichev.foreignexchange.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "conversions")
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

    public Conversion(String currencyFrom,
                      String currencyTo,
                      BigDecimal exchangeRate,
                      BigDecimal amountBefore,
                      BigDecimal amountAfter) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.exchangeRate = exchangeRate;
        this.amountBefore = amountBefore;
        this.amountAfter = amountAfter;
        this.conversionDate = LocalDate.now();
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

    @Column(nullable = false)
    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    @Column(nullable = false)
    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    @Column(nullable = false)
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Column(nullable = false)
    public BigDecimal getAmountBefore() {
        return amountBefore;
    }

    public void setAmountBefore(BigDecimal amountBefore) {
        this.amountBefore = amountBefore;
    }

    @Column(nullable = false)
    public BigDecimal getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(BigDecimal amountAfter) {
        this.amountAfter = amountAfter;
    }

    @Column(nullable = false)
    public LocalDate getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(LocalDate conversionDate) {
        this.conversionDate = conversionDate;
    }
}
