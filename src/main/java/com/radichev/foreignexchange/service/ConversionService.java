package com.radichev.foreignexchange.service;

import com.radichev.foreignexchange.model.ConversionBindingModel;
import com.radichev.foreignexchange.model.ConversionCurrencyViewModel;
import com.radichev.foreignexchange.model.currencyLayerModels.ConversionViewModel;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

public interface ConversionService {

    ConversionCurrencyViewModel exchangeCurrency(ConversionBindingModel conversionBindingModel);

    Page<ConversionViewModel> findAllConversionsByCriteria(String transactionId,
                                                           LocalDate transactionDate,
                                                           PageRequest pageRequest);
}
