package com.radichev.foreignexchange.api;

import com.radichev.foreignexchange.model.RateBindingModel;
import com.radichev.foreignexchange.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("api")
public class RateController {
    private final RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping("/rate")
    public ResponseEntity<BigDecimal> getExchangeRate(@RequestBody RateBindingModel rateBindingModel) {
        return ResponseEntity
                .ok()
                .body(this.rateService.getExchangeRate(rateBindingModel));
    }
}
