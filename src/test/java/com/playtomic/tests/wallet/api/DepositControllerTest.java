package com.playtomic.tests.wallet.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.playtomic.tests.wallet.exception.DepositControllerAdvice;
import com.playtomic.tests.wallet.exception.DepositException;
import com.playtomic.tests.wallet.model.*;
import com.playtomic.tests.wallet.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class DepositControllerTest {


    @Autowired
    MockMvc mockMvc;



    @MockBean
    IDepositService depositService;

    @Autowired
    private DepositController controller;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(
                        DepositControllerAdvice.class)
                .build();
    }



    @Test
    public void processDepositExists() throws Exception {

        DepositResponse expectedResponse= DepositResponse.builder()
                .build();





        when(depositService.processDeposit(any(DepositRequest.class))).thenThrow(DepositException.alreadyExists());



        DepositRequest depositRequest = DepositRequest.builder()
                .amount(new BigDecimal(20))
                .requestId(123456L)
                .cardNumber("123456789")
                .walletId(1L)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(depositRequest );

        MvcResult currentResponse=mockMvc.perform(MockMvcRequestBuilders.post("/api/deposits")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper objectMapper= new ObjectMapper();
        DepositError error = objectMapper.readValue(currentResponse.getResponse().getContentAsString(), DepositError.class);
        assertEquals(DepositError.ERROR_PROCESSING_DEPOSIT_ALREADY_EXIST.getErrorCode(),error.getErrorCode());
        assertNotNull(error.getErrorMessage());

    }

}
