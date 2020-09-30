package com.radichev.foreignexchange.service.serviceImpl;

import com.radichev.foreignexchange.config.CurrencyLayerConfiguration;
import com.radichev.foreignexchange.model.RateBindingModel;
import com.radichev.foreignexchange.model.currencyLayerModels.LiveRate;
import com.radichev.foreignexchange.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Service
public class RateServiceImpl implements RateService {
    private final RestTemplate restTemplate;
    private final CurrencyLayerConfiguration currencyLayerConfiguration;

    @Autowired
    public RateServiceImpl(RestTemplate restTemplate, CurrencyLayerConfiguration currencyLayerConfiguration) {
        this.restTemplate = restTemplate;
        this.currencyLayerConfiguration = currencyLayerConfiguration;
    }

    @Override
    public BigDecimal getExchangeRate(RateBindingModel rateBindingModel) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(currencyLayerConfiguration.getUrl())
                .queryParam("access_key", currencyLayerConfiguration.getAccessKey())
                .queryParam("currencies", rateBindingModel.getCurrencyTo())
                .queryParam("source", rateBindingModel.getCurrencyFrom())
                .queryParam("format", "1");

        LiveRate liveRate = restTemplate.getForObject(builder.toUriString(), LiveRate.class);

        return liveRate
                .getQuotes()
                .get(rateBindingModel.getCurrencyFrom() + rateBindingModel.getCurrencyTo());
    }
}
