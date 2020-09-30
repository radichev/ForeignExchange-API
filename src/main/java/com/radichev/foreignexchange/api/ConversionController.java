package com.radichev.foreignexchange.api;

import com.radichev.foreignexchange.model.ConversionBindingModel;
import com.radichev.foreignexchange.model.ConversionCurrencyViewModel;
import com.radichev.foreignexchange.model.currencyLayerModels.ConversionViewModel;
import com.radichev.foreignexchange.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api")
public class ConversionController {
    private final ConversionService conversionService;

    @Autowired
    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping("/exchange")
    public ResponseEntity<ConversionCurrencyViewModel> exchangeCurrency(@RequestBody ConversionBindingModel conversionBindingModel) {
        return ResponseEntity
                .ok()
                .body(this.conversionService.exchangeCurrency(conversionBindingModel));
    }

    @GetMapping(value = "/conversions/all",
            params = {"transactionId", "transactionDate", "page", "size"})
    public ResponseEntity<Page<ConversionViewModel>> findAllConversionsByCriteria(@RequestParam(value = "transactionId", required = false) String transactionId,
                                                                                  @RequestParam(value = "transactionDate", required = false)
                                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate transactionDate,
                                                                                  @RequestParam(value = "page") int page,
                                                                                  @RequestParam(value = "size") int size) {

        return ResponseEntity
                .ok()
                .body(this.conversionService.findAllConversionsByCriteria(transactionId, transactionDate, PageRequest.of(page, size)));
    }
}
