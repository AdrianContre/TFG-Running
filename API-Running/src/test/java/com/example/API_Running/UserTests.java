package com.example.API_Running;


import com.example.API_Running.dtos.LoginRequest;
import com.example.API_Running.dtos.RegisterRequest;
import com.example.API_Running.models.Runner;
import com.example.API_Running.models.User;
import com.example.API_Running.models.UserDetailsImplementation;
import com.example.API_Running.repository.RunnerRepository;
import com.example.API_Running.repository.TrainerRepository;
import com.example.API_Running.repository.UserRepository;
import com.example.API_Running.services.AuthService;
import com.example.API_Running.services.JwtService;
import com.example.API_Running.services.UserDetailsServiceImplementation;
import com.example.API_Running.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RunnerRepository runnerRepository;

    @MockBean
    private TrainerRepository trainerRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImplementation userDetailsServiceImplementation;




    @TestConfiguration
    static class UserTestConfiguration {
        @Bean
        public UserService userService (UserRepository userRepository, RunnerRepository runnerRepository, TrainerRepository trainerRepository) {
            return new UserService(userRepository, runnerRepository, trainerRepository);
        }

        @Bean
        public AuthService authService (UserRepository userRepository,JwtService jwtService, AuthenticationManager authenticationManager ) {
            return new AuthService(userRepository,jwtService,authenticationManager);
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    /*
    @BeforeEach
    public void setUp () {
        Runner user = new Runner(0,0,0,false);
        user.setUsername("test");
        user.setPassword("1234");
        user.setName("test");
        user.setUsername("test");



        RegisterRequest request = new RegisterRequest("test", "test", "test@gmail.com", "test","1234",0,0,0,false,0);
        authService.register(request);
        LoginRequest request2 = new LoginRequest("test","1234");
        authService.login(request2);
    }
    */


    @Test
    public void whoAmI () {
        System.out.println("Iniciando prueba whoAmI");
        Runner user = new Runner(0,0,0,false);
        user.setUsername("test");
        user.setPassword("1234");
        user.setName("test");
        user.setUsername("test");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        RegisterRequest request = new RegisterRequest("test", "test", "test@gmail.com", "test","1234",0,0,0,false,0);
        ResponseEntity<Object> registerResponse = authService.register(request);
        //assertEquals(registerResponse.getStatusCode(),HttpStatus.OK);

        Mockito.when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        LoginRequest request2 = new LoginRequest("test","1234");
        ResponseEntity<Object> loginResponse = authService.login(request2);
        HashMap<String, Object> responseBody = (HashMap<String, Object>) loginResponse.getBody();
        String token = (String) responseBody.get("token");
        System.out.println("token: " + token);
        assertEquals(loginResponse.getStatusCode(),HttpStatus.OK);

        UserDetailsImplementation userDetails = new UserDetailsImplementation(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);



        try {
            Mockito.when(runnerRepository.findByUsername("test")).thenReturn(Optional.of(user));
            ResponseEntity<Object> response = userService.whoAmI();
            assertEquals(response.getStatusCode(), HttpStatus.OK);
        }
        catch (ResponseStatusException e) {
            fail ("exception found");
        }

    }

}
