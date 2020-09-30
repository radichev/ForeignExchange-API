package com.radichev.foreignexchange.service.serviceImpl;

import com.radichev.foreignexchange.domain.Conversion;
import com.radichev.foreignexchange.exception.ConversionCriteriaException;
import com.radichev.foreignexchange.exception.RateNotFoundException;
import com.radichev.foreignexchange.model.ConversionBindingModel;
import com.radichev.foreignexchange.model.ConversionCurrencyViewModel;
import com.radichev.foreignexchange.model.RateBindingModel;
import com.radichev.foreignexchange.model.currencyLayerModels.ConversionViewModel;
import com.radichev.foreignexchange.repository.ConversionRepository;
import com.radichev.foreignexchange.service.ConversionService;
import com.radichev.foreignexchange.service.RateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionServiceImplTest {
    private ConversionService conversionService;

    @Mock
    private ConversionRepository conversionRepository;

    @Mock
    private RateService rateService;

    private ModelMapper modelMapper = new ModelMapper();

    private Conversion conversion;

    @BeforeEach
    public void setUp() {
        this.conversionService = new ConversionServiceImpl(rateService, modelMapper, conversionRepository);

        conversion = new Conversion("USD", "BGN", BigDecimal.valueOf(1), BigDecimal.valueOf(10), BigDecimal.valueOf(10));
    }

    @Test
    void exchangeCurrencyShouldReturnCorrectResult() {
        RateBindingModel rateBindingModel = new RateBindingModel();
        rateBindingModel.setCurrencyFrom("USD");
        rateBindingModel.setCurrencyTo("BGN");

        when(this.rateService.getExchangeRate(Mockito.any(RateBindingModel.class)))
                .thenReturn(BigDecimal.valueOf(1));

        when(this.conversionRepository.save(Mockito.any(Conversion.class)))
                .thenReturn(conversion);

        ConversionBindingModel conversionBindingModel = new ConversionBindingModel();
        conversionBindingModel.setAmount(BigDecimal.valueOf(10));
        conversionBindingModel.setCurrencyFrom("USD");
        conversionBindingModel.setCurrencyTo("BGN");

        ConversionCurrencyViewModel conversionCurrencyViewModel = this.conversionService.exchangeCurrency(conversionBindingModel);

        Assertions.assertEquals(conversionCurrencyViewModel.getAmountAfter(), conversion.getAmountAfter());
        Assertions.assertEquals(conversionCurrencyViewModel.getId(), conversion.getId());
    }

//    @Test
//    void exchangeCurrencyShouldThrowNotFoundExceptionInCaseOfWrongCurrencyInput() {
//        ConversionBindingModel conversionBindingModel = new ConversionBindingModel();
//        conversionBindingModel.setAmount(BigDecimal.valueOf(10));
//        conversionBindingModel.setCurrencyFrom("EUR");
//        conversionBindingModel.setCurrencyTo("BGN");
//
//
//        Exception exception = Assertions.assertThrows(RateNotFoundException.class, () -> {
//            this.conversionService.exchangeCurrency(conversionBindingModel);
//        });
//
//        Assertions.assertEquals(exception.getMessage(), "Rate not found: EURO");
//    }

    @Test
    void findAllConversionsByCriteriaShouldThrowConversionCriteriaExceptionInCaseOfWrongInput() {
        Exception exception = Assertions.assertThrows(ConversionCriteriaException.class, () -> {
            this.conversionService.findAllConversionsByCriteria("", null, PageRequest.of(0, 5));
        });

        Assertions.assertEquals(exception.getMessage(), "At least one of the criterias should be available either Transaction Id or Transaction Date");
    }

    @Test
    void findAllConversionsByCriteriaShouldPerformCorrectlyWithCorrectInput() {
        Page<Conversion> conversions = new PageImpl<>(List.of(conversion));
        when(conversionRepository.findAllConversionsByCriteria("testId", LocalDate.now(), PageRequest.of(0,5)))
                .thenReturn(conversions);

        Page<ConversionViewModel> testConversionCollection = this.conversionService
                .findAllConversionsByCriteria("testId", LocalDate.now(), PageRequest.of(0, 5));

        Assertions.assertEquals(1, testConversionCollection.getTotalElements());
        ConversionViewModel conversionViewModel = testConversionCollection.get().collect(Collectors.toList()).get(0);

        Assertions.assertEquals(conversionViewModel.getId(), conversion.getId());
        Assertions.assertEquals(conversionViewModel.getAmountAfter(), conversion.getAmountAfter());
        Assertions.assertEquals(conversionViewModel.getAmountBefore(), conversion.getAmountBefore());
        Assertions.assertEquals(conversionViewModel.getConversionDate(), conversion.getConversionDate());
        Assertions.assertEquals(conversionViewModel.getCurrencyFrom(), conversion.getCurrencyFrom());
        Assertions.assertEquals(conversionViewModel.getCurrencyTo(), conversion.getCurrencyTo());
        Assertions.assertEquals(conversionViewModel.getExchangeRate(), conversion.getExchangeRate());
    }
}