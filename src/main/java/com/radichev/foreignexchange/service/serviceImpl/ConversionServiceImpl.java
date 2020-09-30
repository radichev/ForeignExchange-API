package com.radichev.foreignexchange.service.serviceImpl;

import com.radichev.foreignexchange.domain.Conversion;
import com.radichev.foreignexchange.exception.ConversionCriteriaException;
import com.radichev.foreignexchange.model.ConversionBindingModel;
import com.radichev.foreignexchange.model.ConversionCurrencyViewModel;
import com.radichev.foreignexchange.model.RateBindingModel;
import com.radichev.foreignexchange.model.currencyLayerModels.ConversionViewModel;
import com.radichev.foreignexchange.repository.ConversionRepository;
import com.radichev.foreignexchange.service.ConversionService;
import com.radichev.foreignexchange.service.RateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    public ConversionCurrencyViewModel exchangeCurrency(ConversionBindingModel conversionBindingModel) {
        RateBindingModel rateBindingModel = this.modelMapper.map(conversionBindingModel, RateBindingModel.class);

        BigDecimal rate = this.rateService.getExchangeRate(rateBindingModel);

        BigDecimal exchangedAmount = rate.multiply(conversionBindingModel.getAmount());

        Conversion conversion = new Conversion(conversionBindingModel.getCurrencyFrom(),
                                               conversionBindingModel.getCurrencyTo(),
                                               rate,
                                               conversionBindingModel.getAmount(),
                                               exchangedAmount);

        return this.modelMapper.map(this.conversionRepository.save(conversion), ConversionCurrencyViewModel.class);
    }

    @Override
    public Page<ConversionViewModel> findAllConversionsByCriteria(String transactionId,
                                                                  LocalDate transactionDate,
                                                                  PageRequest pageRequest) {

        if (transactionId.equals("") && transactionDate == null) {
            throw new ConversionCriteriaException("At least one of the criterias should be available either Transaction Id or Transaction Date");
        }

        return this.conversionRepository.findAllConversionsByCriteria(transactionId, transactionDate, pageRequest)
                .map(conversion -> this.modelMapper.map(conversion, ConversionViewModel.class));
    }
}
