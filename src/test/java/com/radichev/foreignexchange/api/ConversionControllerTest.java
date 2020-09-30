package com.radichev.foreignexchange.api;

import com.radichev.foreignexchange.domain.Conversion;
import com.radichev.foreignexchange.model.ConversionBindingModel;
import com.radichev.foreignexchange.repository.ConversionRepository;
import com.radichev.foreignexchange.service.ConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.radichev.foreignexchange.api.TestUtils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
class ConversionControllerTest {

    private final MockMvc mockMvc;
    private final ConversionRepository conversionRepository;

    @Autowired
    ConversionControllerTest(MockMvc mockMvc,
                             ConversionRepository conversionRepository) {
        this.mockMvc = mockMvc;
        this.conversionRepository = conversionRepository;
    }

    private String CONVERSION_ID;
    private Conversion conversion;

    @BeforeEach
    public void setUp() {
        this.conversionRepository.deleteAll();

        conversion = new Conversion();
        conversion.setAmountAfter(BigDecimal.valueOf(10));
        conversion.setAmountBefore(BigDecimal.valueOf(5));
        conversion.setConversionDate(LocalDate.now());
        conversion.setCurrencyFrom("USD");
        conversion.setCurrencyTo("BGN");
        conversion.setExchangeRate(BigDecimal.valueOf(2));
        CONVERSION_ID = this.conversionRepository.save(conversion).getId();
    }

    @Test
    void exchangeCurrencyShouldReturnCorrectResult() throws Exception {
        ConversionBindingModel conversionBindingModel = new ConversionBindingModel();
        conversionBindingModel.setAmount(BigDecimal.valueOf(10));
        conversionBindingModel.setCurrencyFrom("USD");
        conversionBindingModel.setCurrencyTo("BGN");

        this.mockMvc.perform(post("/api/exchange")
                .content(asJsonString(conversionBindingModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findAllConversionsByCriteriaShouldPerformCorrectly() throws Exception {
        this.mockMvc.perform(get("/api/conversions/all")
                .queryParam("transactionId", CONVERSION_ID)
                .queryParam("transactionDate", conversion.getConversionDate().toString())
                .queryParam("page", "0")
                .queryParam("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(CONVERSION_ID)))
                .andExpect(jsonPath("$.content[0].amountAfter", is(conversion.getAmountAfter().doubleValue())))
                .andExpect(jsonPath("$.content[0].amountBefore", is(conversion.getAmountBefore().doubleValue())))
                .andExpect(jsonPath("$.content[0].conversionDate", is(conversion.getConversionDate().toString())))
                .andExpect(jsonPath("$.content[0].currencyFrom", is(conversion.getCurrencyFrom())))
                .andExpect(jsonPath("$.content[0].currencyTo", is(conversion.getCurrencyTo())))
                .andExpect(jsonPath("$.content[0].exchangeRate", is(conversion.getExchangeRate().doubleValue())));
    }
}