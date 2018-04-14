package net.wohlfart.mercury.controller;

import net.wohlfart.mercury.security.JwtTokenUtil;
import net.wohlfart.mercury.service.UserService;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class SecurityControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserService userService;

    @InjectMocks
    @Autowired
    private SecurityController securityController;

    private MockMvc mvc;

    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
            ".eyJzdWIiOiJyYW5kb20tbmFtZSIsInJvbGUiOiJVU0VSIiwiY3JlYXRlZCI6MX0" +
            ".idLq2N5BJZiqkylavUVJTkGKiNlc_5xdFHISCoke3ss";


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(securityController).build();
    }

    /*
    @Test(timeout=1000)
    public void signUpTest() throws Exception {
        Subject user = DummyDataGenerator.getUsers(1).get(0);
        UserDetailsImpl jwtUser = new UserDetailsImpl(0L, user.getName(), user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRoles()[0])));

        String password = passwordEncoder.encode(user.getPassword());
        SecurityController.AuthenticationRequest request = new SecurityController.AuthenticationRequest(
                user.getName(), user.getEmail(), password);

        SecurityController.AuthenticationRequest expectedResponse = new SecurityController.AuthenticationRequest(TOKEN);

        when(userService.create(any(Subject.class))).thenReturn(user);

        when(userDetailsService.loadUserByUsername(anyString()))
                .thenReturn(jwtUser);

        when(jwtTokenUtil.generateToken(any(UserDetails.class)))
                .thenReturn(TOKEN);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(any(Authentication.class));

        MvcResult result = mvc.perform(post(AuthController.SIGNUP_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JsonMapper.toJson(request)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(userService, times(1)).create(any(Subject.class));
        verify(userDetailsService, times(1)).loadUserByUsername(anyString());
        verify(jwtTokenUtil, times(1)).generateToken(any(UserDetails.class));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertEquals("Expected HTTP status 200", 200, status);
        assertNotNull("Token shouldn't be NULL", content);
        assertTrue("Content shouldn't be empty", content.trim().length() > 0);
        assertEquals("Should return appropriate token", expectedResponse.toString(), content);
    }

    @Test(timeout=10000)
    public void signInTest() throws Exception {
        Subject user = DummyDataGenerator.getUsers(1).get(0);

        String password = passwordEncoder.encode(user.getPassword());
        UserDetailsImpl jwtUser = new UserDetailsImpl(0L, user.getName(), user.getEmail(), password,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRoles())));

        SecurityController.AuthenticationRequest request = new SecurityController.AuthenticationRequest(
                user.getName(), user.getEmail(), user.getPassword());

        SecurityController.AuthenticationRequest expectedResponse = new SecurityController.AuthenticationRequest(TOKEN);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(jwtUser);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn(TOKEN);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(any(Authentication.class));

        MvcResult result = mvc.perform(post(AuthController.SIGNIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JsonMapper.toJson(request)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(userDetailsService, times(1)).loadUserByUsername(anyString());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertEquals("Expected HTTP status 200", 200, status);
        assertNotNull("Token shouldn't be NULL", content);
        assertTrue("Content shouldn't be empty", content.trim().length() > 0);
        assertEquals("Should return appropriate token", expectedResponse.toString(), content);
    }

    @Test(timeout=10000)
    public void refreshTest() throws Exception {
        SecurityController.AuthenticationRequest expectedResponse = new SecurityController.AuthenticationRequest(TOKEN);

        when(jwtTokenUtil.refreshToken(anyString())).thenReturn(TOKEN);

        MvcResult result = mvc.perform(post(REFRESH_TOKEN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JsonMapper.toJson(TOKEN)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(jwtTokenUtil, times(1)).refreshToken(anyString());

        assertEquals("Expected HTTP status 200", 200, status);
        assertNotNull("Token shouldn't be NULL", content);
        assertTrue("Content shouldn't be empty", content.trim().length() > 0);
        assertEquals("Should return appropriate token", expectedResponse.toString(), content);
    }

    */
}
