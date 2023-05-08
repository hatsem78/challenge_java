package com.challenge_java.challenge_java;

import com.challenge_java.challenge_java.controller.AuthController;
import com.challenge_java.challenge_java.model.entity.ERole;
import com.challenge_java.challenge_java.model.entity.Role;
import com.challenge_java.challenge_java.model.entity.User;
import com.challenge_java.challenge_java.model.services.RoleServiceImpl;
import com.challenge_java.challenge_java.model.services.UserServicesImpl;
import com.challenge_java.challenge_java.request.PhoneRequest;
import com.challenge_java.challenge_java.request.SignupRequest;
import com.challenge_java.challenge_java.security.jwt.JwtUtils;
import com.challenge_java.challenge_java.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTests {

    @MockBean
    private RoleServiceImpl roleService;

    private MockMvc mockMvc;

    private SignupRequest signupRequest;

    private UserServicesImpl userServices;

    private ObjectMapper mapper = new ObjectMapper();

    private User user;

    private PhoneRequest phone;

    private PasswordEncoder encoder;

    private JwtUtils jwtUtils;

    @MockBean
    private AuthController authController;

    private AuthenticationManager authenticationManager;
    public UtilsTest utilsTest = new UtilsTest();

    private Utils utils;

    @TestConfiguration
    protected static class Config {
        @Bean
        public UtilsTest utilsTest() {
            return new UtilsTest();
        }


    }

    @BeforeEach
    void setup() {

        this.authController = Mockito.mock(AuthController.class);
        this.jwtUtils = new JwtUtils();
        this.encoder = Mockito.mock(PasswordEncoder.class);
        this.userServices = Mockito.mock(UserServicesImpl.class);
        this.roleService = Mockito.mock(RoleServiceImpl.class);
        this.authenticationManager = Mockito.mock(AuthenticationManager.class);


        this.utils = new Utils();


        ReflectionTestUtils.setField(this.authController, "jwtUtils", this.jwtUtils);
        ReflectionTestUtils.setField(this.authController, "encoder", this.encoder);
        ReflectionTestUtils.setField(this.authController, "roleService", this.roleService);
        ReflectionTestUtils.setField(this.authController, "roleService", this.roleService);
        ReflectionTestUtils.setField(this.authController, "utils", this.utils);
        user = new User();

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new AuthController(
                                authenticationManager, userServices,
                                encoder,
                                jwtUtils,
                                utils,
                                roleService
                        )
                )
                .build();

        user.setEmail("user@gmail.com");
        user.setPassword("passR111");
        user.setUsername("user");

        phone = new PhoneRequest();
        phone.setNumber(87650009L);
        phone.setCityCode(7);
        phone.setContrycode("25");

        List<PhoneRequest> phoneList = new ArrayList<>();
        phoneList.add(phone);

        signupRequest = SignupRequest.build(user, phoneList);
    }

    @Test
    void test_signup_returnsOk() throws Exception {
        List<Role> roleList = utilsTest.getRoleList();
        Optional<Role> role = Optional.of(roleList.get(0));
        doReturn(role).when(roleService).findByName(ERole.ROLE_USER);
        doReturn(user).when(userServices).save(user);
        doReturn(Optional.of(user)).when(userServices).findByUsername(user.getUsername());
        doReturn(new ResponseEntity<>(roleList, HttpStatus.OK)).when(authController).registerUser(signupRequest);
        mockMvc.perform(post("/api/auth/signup")
                        .content(mapper.writeValueAsString(signupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void test_signup_userName_take() throws Exception {
        doReturn(true).when(userServices).existsByUsername(user.getUsername());
        String returnedId = mockMvc.perform(post("/api/auth/signup")
                        .content(mapper.writeValueAsString(signupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(returnedId, contains("Username is already taken").isEmpty());
    }

    @Test
    void test_signup_existsByEmail() throws Exception {
        doReturn(true).when(userServices).existsByEmail(user.getEmail());
        String returnedId = mockMvc.perform(post("/api/auth/signup")
                        .content(mapper.writeValueAsString(signupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(returnedId, contains("Username is already take").isEmpty());
    }

    @Test
    void test_signup_usesGivenEmailAndEncodedPassword() throws Exception {

        List<Role> roleList = utilsTest.getRoleList();
        Optional<Role> role = Optional.of(roleList.get(0));
        doReturn(role).when(roleService).findByName(ERole.ROLE_USER);

        mockMvc.perform(post("/api/auth/signup")
                .content(mapper.writeValueAsString(signupRequest))
                .contentType(MediaType.APPLICATION_JSON));


        assertThat(user.getEmail(), equalTo("user@gmail.com"));
    }

}
