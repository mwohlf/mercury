package net.wohlfart.mercury.service;

import net.wohlfart.mercury.model.User;
import net.wohlfart.mercury.repository.UserRepository;
import net.wohlfart.mercury.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


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
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        final Set<GrantedAuthority> authorities = new HashSet<>();
        return new UserDetailsImpl(user.getId(), user.getName(), user.getPassword(), authorities);
    }

}
