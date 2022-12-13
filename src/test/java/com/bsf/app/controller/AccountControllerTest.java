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

import com.bsf.app.service.AccountService;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	@Test
	public void createAccountTest() throws Exception {
		this.mockMvc.perform(put("/api/account/create").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Prasanna\", \"number\":\"202206101986\", \"balance\":\"340.50\"}")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated());
	}

}
