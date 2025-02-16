package com.example.API_Running.services;

import com.example.API_Running.dtos.AuthPolarDTO;
import com.example.API_Running.models.PolarAccount;
import com.example.API_Running.models.User;
import com.example.API_Running.models.UserDetailsImplementation;
import com.example.API_Running.repository.PolarAccountRepository;
import com.example.API_Running.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PolarService {

    private final PolarAccountRepository polarAccountRepository;
    private final UserRepository userRepository;

    @Autowired
    public PolarService(PolarAccountRepository polarAccountRepository, UserRepository userRepository) {
        this.polarAccountRepository = polarAccountRepository;
        this.userRepository = userRepository;
    }




    public ResponseEntity<Object> authenticate(AuthPolarDTO authPolarDTO) {
        HashMap<String, Object> data = new HashMap<>();
        String code = authPolarDTO.getCode();
        if (code == null) {
            data.put("error", "code is required");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        String body = "grant_type=authorization_code"
                + "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(authPolarDTO.getRedirect_uri(), StandardCharsets.UTF_8);

        // Configurar la solicitud
        String polarUrl = "https://polarremote.com/v2/oauth2/token";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Accept", "application/json");
        
        String auth = authPolarDTO.getClient_id() + ":" + authPolarDTO.getClient_secret();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        // Enviar la solicitud POST
        ResponseEntity<String> response = restTemplate.postForEntity(polarUrl, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            try {

                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> jsonResponse = objectMapper.readValue(response.getBody(), Map.class);


                String accessToken = (String) jsonResponse.get("access_token");
                String token_type = (String) jsonResponse.get("token_type");
                Integer expiresIn = (int) jsonResponse.get("expires_in");
                Integer polarUserId = (int) jsonResponse.get("x_user_id");

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
                User user = u.getUser();
                Optional<PolarAccount> query = this.polarAccountRepository.findByUser(user);
                if (!query.isPresent()) {
                    PolarAccount pa = new PolarAccount(accessToken, token_type, polarUserId, expiresIn, user);
                    user.setPolarAccount(pa);
                    this.polarAccountRepository.save(pa);
                    this.userRepository.save(user);
                }
                else {
                    PolarAccount pa = query.get();
                    pa.setAccessToken(accessToken);
                    pa.setToken_type(token_type);
                    pa.setTokenExpiry(expiresIn);
                    pa.setPolarUserId(polarUserId);
                    this.polarAccountRepository.save(pa);
                }

                data.put("data", "Cuenta de Polar vinculada con éxito");
                return new ResponseEntity<>(data, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                data.put("error", "Error al procesar la respuesta de Polar");
                return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            data.put("error", "Error al obtener el token de Polar");
            return new ResponseEntity<>(data, response.getStatusCode());
        }
    }
}
