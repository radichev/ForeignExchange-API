package com.radichev.foreignexchange.service.serviceImpl;

import com.radichev.foreignexchange.config.CurrencyLayerConfiguration;
import com.radichev.foreignexchange.exception.RateNotFoundException;
import com.radichev.foreignexchange.model.RateBindingModel;
import com.radichev.foreignexchange.model.currencyLayerModels.LiveRate;
import com.radichev.foreignexchange.service.RateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {
    private RateService rateService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CurrencyLayerConfiguration currencyLayerConfiguration;

    private RateBindingModel rateBindingModel;

    @BeforeEach
    public void setUp() {
        this.rateService = new RateServiceImpl(restTemplate, currencyLayerConfiguration);

        rateBindingModel = new RateBindingModel();
        rateBindingModel.setCurrencyFrom("USD");
        rateBindingModel.setCurrencyTo("BGN");
    }

    @Test
    void getExchangeRateShouldReturnCorrectResult() {
        LiveRate liveRate = new LiveRate();
        liveRate.setSuccess(true);
        liveRate.setSource("USDBGN");
        liveRate.setQuotes(Map.of("USDBGN", BigDecimal.valueOf(1)));

        when(currencyLayerConfiguration.getAccessKey())
                .thenReturn("testKey");

        when(currencyLayerConfiguration.getUrl())
                .thenReturn("testUrl");

        when(restTemplate.getForObject(Mockito.anyString(), Mockito.any()))
                .thenReturn(liveRate);

        BigDecimal testRate = this.rateService.getExchangeRate(rateBindingModel);

        Assertions.assertEquals(liveRate.getQuotes().get("USDBGN"), testRate);
    }

    @Test
    void getExchangeRateShouldThrowRateNotFoundExceptionInCaseOfIncorrectInput() {
        when(currencyLayerConfiguration.getAccessKey())
                .thenReturn("testKey");

        when(currencyLayerConfiguration.getUrl())
                .thenReturn("testUrl");

        when(restTemplate.getForObject(Mockito.anyString(), Mockito.any()))
                .thenReturn(new LiveRate());

        Exception exception = Assertions.assertThrows(RateNotFoundException.class, () -> {
            this.rateService.getExchangeRate(rateBindingModel);
        });

        Assertions.assertEquals(exception.getMessage(), "Rate not found: USD");
    }
}