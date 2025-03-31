package com.playtomic.tests.wallet;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtomic.tests.wallet.api.WalletController;
import com.playtomic.tests.wallet.exception.WalletControllerAdvice;
import com.playtomic.tests.wallet.exception.WalletException;
import com.playtomic.tests.wallet.model.*;
import com.playtomic.tests.wallet.service.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.ErrorResponse;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

@SpringBootTest
@ActiveProfiles(profiles = "test")

public class WalletApplicationIT {


	@Autowired
	MockMvc mockMvc;

	@MockBean
	IWalletService walletService;

	@MockBean
	IDepositService depositService;

	@Autowired
	private WalletController controller;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setControllerAdvice(
						WalletControllerAdvice.class)
				.build();
	}


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

	@Test
	public void getWalletNotFound() throws Exception {

		WalletError expectedWalletError= WalletError.WALLET_NOT_FOUND;

		when(walletService.getWalletByID(2L)).thenThrow(WalletException.notFound());

		MvcResult currentResponse=mockMvc.perform(MockMvcRequestBuilders.get("/api/wallets/" + 2L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();


		ObjectMapper objectMapper= new ObjectMapper();
		WalletError currrentWalletError = objectMapper.readValue(currentResponse.getResponse().getContentAsString(), WalletError.class);


		assertEquals(expectedWalletError.getErrorCode(),currrentWalletError.getErrorCode());
		assertEquals(expectedWalletError.getErrorMessage(),currrentWalletError.getErrorMessage());

	}



}
