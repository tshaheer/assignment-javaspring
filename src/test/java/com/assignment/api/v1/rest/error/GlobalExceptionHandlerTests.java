package com.assignment.api.v1.rest.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@AutoConfigureMockMvc
@SpringBootTest
public class GlobalExceptionHandlerTests {
	
	@Autowired
    private MockMvc mockMvc;


}
