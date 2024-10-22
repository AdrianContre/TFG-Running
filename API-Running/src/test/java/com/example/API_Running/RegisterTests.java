package com.example.API_Running;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.API_Running.controllers.AuthController;
import com.example.API_Running.dtos.RegisterRequest;
import com.example.API_Running.repository.UserRepository;
import com.example.API_Running.services.AuthService;
import com.example.API_Running.services.JwtService;
import com.example.API_Running.services.UserDetailsServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@WebMvcTest(AuthController.class)
public class RegisterTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String END_POINT = "/api/v1/auth/register";

    @MockBean
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    UserDetailsServiceImplementation userDetailsServiceImplementation;

    @MockBean
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    public void testRegisterSuccess() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test");
        request.setSurname("Test surname");
        request.setUsername("test");
        request.setMail("test@gmail.com");
        request.setPassword("1234");
        request.setWeight(70);
        request.setHeight(180);
        request.setFcMax(180);
        request.setTrainer(false);

        String requestBody = objectMapper.writeValueAsString(request);


        mockMvc.perform(post(END_POINT)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());


    }

    @Test
    @WithMockUser
    public void testRegisterParamsLeft() throws Exception {
        RegisterRequest paramsLeft = new RegisterRequest();
        paramsLeft.setSurname("Test surname");
        paramsLeft.setUsername("test");
        paramsLeft.setPassword("1234");
        paramsLeft.setWeight(70);
        paramsLeft.setHeight(180);
        paramsLeft.setFcMax(180);
        paramsLeft.setTrainer(false);

        String body = objectMapper.writeValueAsString(paramsLeft);


        mockMvc.perform(post(END_POINT)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());


    }
}



