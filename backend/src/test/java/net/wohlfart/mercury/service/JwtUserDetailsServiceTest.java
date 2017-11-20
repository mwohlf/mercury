package net.wohlfart.mercury.service;

import net.wohlfart.mercury.App;
import net.wohlfart.mercury.BaseTest;
import net.wohlfart.mercury.model.User;
import net.wohlfart.mercury.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
public class JwtUserDetailsServiceTest extends BaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(timeout = 3000)
    public void loadUserByUsernameTest() {
        User user = User.builder().email("some@email.com").build();
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        UserDetails fetchedUserDetails = jwtUserDetailsService.loadUserByUsername("random name");

        verify(userRepository, times(1)).findByUsername(anyString());
        assertNotNull("Fetched user details shouldn't be NULL", fetchedUserDetails);
        assertEquals("Should return appropriate username", user.getUsername(), fetchedUserDetails.getUsername());
        assertEquals("Should return appropriate password", user.getPassword(), fetchedUserDetails.getPassword());
    }

    @Test(timeout = 3000, expected = UsernameNotFoundException.class)
    public void loadUserWhichNotExistsTest() {
        when(userRepository.findByUsername(anyString())).thenThrow(UsernameNotFoundException.class);
        jwtUserDetailsService.loadUserByUsername("random name");
        verify(userRepository, times(1)).findByUsername(anyString());
        verifyNoMoreInteractions();
    }

}
