package com.playtomic.tests.wallet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.playtomic.tests.wallet.api.DepositController;
import com.playtomic.tests.wallet.api.WalletController;
import com.playtomic.tests.wallet.exception.DepositControllerAdvice;
import com.playtomic.tests.wallet.exception.WalletControllerAdvice;
import com.playtomic.tests.wallet.model.DepositRequest;
import com.playtomic.tests.wallet.model.DepositResponse;
import com.playtomic.tests.wallet.model.WalletResponse;
import com.playtomic.tests.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WalletServiceIntegrationTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    @Order(1)
    public void testGetExistingWallet() throws Exception
    {

        MvcResult currentResponse= mockMvc.perform(MockMvcRequestBuilders.get("/api/wallets/" + 1L )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper objectMapper= new ObjectMapper();
        WalletResponse walletResponse = objectMapper.readValue(currentResponse.getResponse().getContentAsString(), WalletResponse.class);

        assertNotNull(walletResponse);
        assertEquals("EuroWallet",walletResponse.getName());
        assertEquals(0,walletResponse.getDepositList().size());
        assertEquals(new BigDecimal("0.00"),walletResponse.getBalance());

    }


    @Test
    @Order(2)
    public void testProcessDeposit() throws Exception
    {
        DepositRequest depositRequest = DepositRequest.builder()
                .amount(new BigDecimal("10.00"))
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
        DepositResponse depositResponse = objectMapper.readValue(currentResponse.getResponse().getContentAsString(), DepositResponse.class);


        assertNotNull(depositResponse);
        assertEquals(new BigDecimal("10.00"),depositResponse.getAmount());
        assertEquals("EUR",depositResponse.getCurrency());
        assertEquals("APPROVED",depositResponse.getStatus());




    }

    @Test
    @Transactional
    @Order(3)
    public void testGetEXistingWalletNewDeposit()  throws Exception
    {

        MvcResult currentResponse= mockMvc.perform(MockMvcRequestBuilders.get("/api/wallets/" + 1L )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper objectMapper= new ObjectMapper();
        WalletResponse walletResponse = objectMapper.readValue(currentResponse.getResponse().getContentAsString(), WalletResponse.class);

        assertNotNull(walletResponse);
        assertEquals("EuroWallet",walletResponse.getName());
        assertEquals(1,walletResponse.getDepositList().size());
        assertEquals(new BigDecimal("10.00"),walletResponse.getBalance());


    }

}
