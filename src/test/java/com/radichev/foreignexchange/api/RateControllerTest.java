package com.radichev.foreignexchange.api;

import com.radichev.foreignexchange.model.RateBindingModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.radichev.foreignexchange.api.TestUtils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RateControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    RateControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void getExchangeRateShouldReturnCorrectStatusCode() throws Exception {
        RateBindingModel rateBindingModel = new RateBindingModel();
        rateBindingModel.setCurrencyFrom("USD");
        rateBindingModel.setCurrencyTo("BGN");

        this.mockMvc.perform(post("/api/rate")
                .content(asJsonString(rateBindingModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}