package net.wohlfart.mercury.service;

import net.wohlfart.mercury.model.User;
import net.wohlfart.mercury.repository.UserRepository;
import net.wohlfart.mercury.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Finds UserDetails by given username
     * @param username which is used to search user
     * @return UserDetails
     * @throws UsernameNotFoundException if user with given name does not exists
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        return new UserDetailsImpl(user.getId(), user.getName(), user.getPassword(), Collections.emptySet());
    }

}
