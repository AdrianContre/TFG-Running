package com.example.API_Running;

import com.example.API_Running.controllers.AuthController;
import com.example.API_Running.dtos.LoginRequest;
import com.example.API_Running.models.Runner;
import com.example.API_Running.models.User;
import com.example.API_Running.repository.UserRepository;
import com.example.API_Running.services.AuthService;
import com.example.API_Running.services.JwtService;
import com.example.API_Running.services.UserDetailsServiceImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class LoginTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String END_POINT = "/api/v1/auth/login";

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

    private Runner testUser;

    @BeforeEach
    public void setUp() {
        testUser = new Runner();
        testUser.setName("test");
        testUser.setUsername("test");
        testUser.setPassword(new BCryptPasswordEncoder().encode("1234"));
        testUser.setMail("test@example.com");
        testUser.setSurname("test");
        testUser.setWeight(70);
        testUser.setHeight(180);
        testUser.setFcMax(200);
        testUser.setIsTrainer(false);
    }

    @Test
    @WithMockUser
    public void testLoginSuccess() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("test");
        request.setPassword("1234");

        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(END_POINT)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }


}


