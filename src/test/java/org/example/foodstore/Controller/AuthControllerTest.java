package org.example.foodstore.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.foodstore.FoodDao.AuthRequest;
import org.example.foodstore.FoodDao.RegisterRequest;
import org.example.foodstore.FoodDao.User;
import org.example.foodstore.Repository.UserRepository;
import org.example.foodstore.Service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TEST_USER = "test_jwt_user";
    private static final String TEST_PASSWORD = "testPassword123";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        // Clean up or ensure test user exists with hashed password
        userRepository.findByName(TEST_USER).ifPresent(userRepository::delete);

        User user = new User();
        user.setName(TEST_USER);
        user.setPassword(passwordEncoder.encode(TEST_PASSWORD));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    @Test
    @DisplayName("Public health endpoint should be accessible without token")
    void healthCheckShouldBePublic() throws Exception {
        mockMvc.perform(get("/api/foods/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Protected foods endpoint should return 401 without token")
    void protectedEndpointWithoutTokenReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/foods"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value(401));
    }

    @Test
    @DisplayName("Protected foods endpoint should succeed with valid JWT Bearer token")
    void protectedEndpointWithValidJwtTokenReturnsOk() throws Exception {
        String token = jwtService.generateToken(TEST_USER);

        mockMvc.perform(get("/api/foods")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Login with valid credentials returns JWT token")
    void loginWithValidCredentialsReturnsToken() throws Exception {
        AuthRequest authRequest = new AuthRequest(TEST_USER, TEST_PASSWORD);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").isNotEmpty())
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.data.username").value(TEST_USER));
    }

    @Test
    @DisplayName("Login with invalid credentials returns 401 Unauthorized")
    void loginWithInvalidCredentialsReturnsUnauthorized() throws Exception {
        AuthRequest authRequest = new AuthRequest(TEST_USER, "wrongPassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value(401));
    }

    @Test
    @DisplayName("Register new user returns 201 Created and JWT token")
    void registerUserReturnsCreatedAndToken() throws Exception {
        String newUser = "new_signup_user_" + System.currentTimeMillis();
        RegisterRequest registerRequest = new RegisterRequest(newUser, "newSecret123", "ROLE_USER");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").isNotEmpty())
                .andExpect(jsonPath("$.data.username").value(newUser));

        // Clean up
        userRepository.findByName(newUser).ifPresent(userRepository::delete);
    }
}
