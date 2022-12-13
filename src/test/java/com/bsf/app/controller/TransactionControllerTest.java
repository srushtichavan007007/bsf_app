package com.bsf.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bsf.app.service.TransactionService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionService transactionService;

	@Test
	public void transferTest() throws Exception {
		this.mockMvc.perform(put("/api/transfer").contentType(MediaType.APPLICATION_JSON)
				.content("{\"fromAccount\":\"202223111987\", \"toAccount\":\"202206101986\", \"amount\":\"49.75\"}")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isAccepted());
	}

}
