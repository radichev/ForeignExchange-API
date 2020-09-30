package com.radichev.foreignexchange.service.serviceImpl;

import com.radichev.foreignexchange.domain.Conversion;
import com.radichev.foreignexchange.model.ConversionBindingModel;
import com.radichev.foreignexchange.model.ConversionViewModel;
import com.radichev.foreignexchange.model.RateBindingModel;
import com.radichev.foreignexchange.repository.ConversionRepository;
import com.radichev.foreignexchange.service.ConversionService;
import com.radichev.foreignexchange.service.RateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConversionServiceImpl implements ConversionService {
    private final RateService rateService;
    private final ModelMapper modelMapper;
    private final ConversionRepository conversionRepository;

    @Autowired
    public ConversionServiceImpl(RateService rateService,
                                 ModelMapper modelMapper,
                                 ConversionRepository conversionRepository) {
        this.rateService = rateService;
        this.modelMapper = modelMapper;
        this.conversionRepository = conversionRepository;
    }

    @Override
    public ConversionViewModel exchangeCurrency(ConversionBindingModel conversionBindingModel) {
        RateBindingModel rateBindingModel = this.modelMapper.map(conversionBindingModel, RateBindingModel.class);

        BigDecimal rate = this.rateService.getExchangeRate(rateBindingModel);

        BigDecimal exchangedAmount = rate.multiply(conversionBindingModel.getAmount());

        Conversion conversion = new Conversion(conversionBindingModel.getCurrencyFrom(),
                                               conversionBindingModel.getCurrencyTo(),
                                               rate,
                                               conversionBindingModel.getAmount(),
                                               exchangedAmount);

        return this.modelMapper.map(this.conversionRepository.save(conversion), ConversionViewModel.class);
    }
}
