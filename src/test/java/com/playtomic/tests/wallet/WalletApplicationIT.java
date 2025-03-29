package com.playtomic.tests.wallet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtomic.tests.wallet.model.WalletResponse;
import com.playtomic.tests.wallet.service.StripeRestTemplateResponseErrorHandler;
import com.playtomic.tests.wallet.service.WalletService;
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

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class WalletApplicationIT {


	@Autowired
	MockMvc mockMvc;
	RestTemplate restTemplate;





	@Test
	public void getWalletSuccess() throws Exception {

		WalletResponse expectedResponse= WalletResponse.builder().playerEmailAddress("ranchez@gmail.com")
				.balance(new BigDecimal(0))
				.currency("EUR")
				.name("EuroWallet")
				.build();


		MvcResult currentResponse= mockMvc.perform(MockMvcRequestBuilders.get("/api/wallet")
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

		WalletResponse expectedResponse= WalletResponse.builder().playerEmailAddress("ranchez@gmail.com")
				.balance(new BigDecimal(0))
				.currency("EUR")
				.name("EuroWallet")
				.build();

		MvcResult currentResponse=mockMvc.perform(MockMvcRequestBuilders.get("/api/wallet/" + 999L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		ObjectMapper objectMapper= new ObjectMapper();
		WalletResponse walletResponse = objectMapper.readValue(currentResponse.getResponse().getContentAsString(), WalletResponse.class);


		assertNull(walletResponse);

	}
}
