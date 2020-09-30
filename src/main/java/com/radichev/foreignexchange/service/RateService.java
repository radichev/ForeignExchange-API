package com.radichev.foreignexchange.service;

import com.radichev.foreignexchange.model.RateBindingModel;

import java.math.BigDecimal;

public interface RateService {
    BigDecimal getExchangeRate(RateBindingModel rateBindingModel);
}
