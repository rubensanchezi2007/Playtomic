package com.playtomic.tests.wallet;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtomic.tests.wallet.model.*;
import com.playtomic.tests.wallet.service.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class WalletApplicationIT {


	@Autowired
	MockMvc mockMvc;
	RestTemplate restTemplate;

	@MockBean
	IWalletService walletService;

	@MockBean
	IDepositService depositService;

	@Test
	public void getWalletSuccess() throws Exception {



		Wallet wallet = new Wallet();
		wallet.setBalance(new BigDecimal(0));
		wallet.setName("EuroWallet");
		wallet.setDepositList(new ArrayList<Deposit>());
		wallet.setCurrency("EUR");


		when(walletService.getWalletByID(1L)).thenReturn(wallet);

		WalletResponse expectedResponse= WalletResponse.builder()
				.balance(wallet.getBalance())
				.currency(wallet.getCurrency())
				.name(wallet.getName())
				.depositList(wallet.getDepositList())
				.build();


		MvcResult currentResponse= mockMvc.perform(MockMvcRequestBuilders.get("/api/wallets/" + 1L )
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		ObjectMapper objectMapper= new ObjectMapper();
		WalletResponse walletResponse = objectMapper.readValue(currentResponse.getResponse().getContentAsString(), WalletResponse.class);

		assertNotNull(walletResponse);
		assertEquals(walletResponse.getName(),expectedResponse.getName());

	}

	/*@Test
	public void getWalletNotFound() throws Exception {

		WalletResponse expectedResponse= WalletResponse.builder()
				.build();

		when(walletService.getWalletByID(1L)).thenReturn(new Wallet());

		MvcResult currentResponse=mockMvc.perform(MockMvcRequestBuilders.get("/api/wallets/" + 1L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();



	}*/


	/*@Test
	public void processDepositSuccess() throws Exception {

		DepositResponse expectedResponse= DepositResponse.builder()
				.build();

		//when(depositService.processDeposit(any(DepositRequest.class))).thenReturn(expectedResponse);

		MvcResult currentResponse=mockMvc.perform(MockMvcRequestBuilders.post("/api/deposits/")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();



	}

	@Test
	public void processDepositError() throws Exception {

		DepositResponse expectedResponse= DepositResponse.builder()
				.build();

		//when(depositService.processDeposit(any(DepositRequest.class))).thenReturn(expectedResponse);

		MvcResult currentResponse=mockMvc.perform(MockMvcRequestBuilders.post("/api/deposits/")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		ObjectMapper objectMapper= new ObjectMapper();
		DepositError error = objectMapper.readValue(currentResponse.getResponse().getContentAsString(), DepositError.class);
        assertNotNull(error.getErrorCode());
		assertNotNull(error.getErrorMessage());

	}*/

}
