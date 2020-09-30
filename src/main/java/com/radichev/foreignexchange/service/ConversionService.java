package com.radichev.foreignexchange.service;

import com.radichev.foreignexchange.model.ConversionBindingModel;
import com.radichev.foreignexchange.model.ConversionViewModel;

public interface ConversionService {

    ConversionViewModel exchangeCurrency(ConversionBindingModel conversionBindingModel);
}
