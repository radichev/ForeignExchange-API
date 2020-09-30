package com.radichev.foreignexchange.model.currencyLayerModels;

import java.math.BigDecimal;
import java.util.Map;

public class LiveRate {
    private boolean success;
    private String source;
    private Map<String, BigDecimal> quotes;
    private String terms;
    private String privacy;
    private Long timestamp;

    public LiveRate() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, BigDecimal> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<String, BigDecimal> quotes) {
        this.quotes = quotes;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
