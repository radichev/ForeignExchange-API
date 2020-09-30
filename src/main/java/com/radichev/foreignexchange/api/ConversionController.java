package com.radichev.foreignexchange.api;

import com.radichev.foreignexchange.model.ConversionBindingModel;
import com.radichev.foreignexchange.model.ConversionViewModel;
import com.radichev.foreignexchange.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ConversionController {
    private final ConversionService conversionService;

    @Autowired
    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping("/exchange")
    public ResponseEntity<ConversionViewModel> exchangeCurrency(@RequestBody ConversionBindingModel conversionBindingModel) {
        return ResponseEntity
                .ok()
                .body(this.conversionService.exchangeCurrency(conversionBindingModel));
    }
}
