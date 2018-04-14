package net.wohlfart.mercury.service;

import net.wohlfart.mercury.model.Subject;
import net.wohlfart.mercury.repository.UserRepository;
import net.wohlfart.mercury.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public UserDetails loadUserById(Long id) {
        Subject subject = userRepository.findById(id);
        if (subject == null) {
            throw new UsernameNotFoundException("iid: " + id);
        }
        final Set<GrantedAuthority> authorities = new HashSet<>();
        return new UserDetailsImpl(subject.getId(), subject.getUsername(), subject.getPassword(), authorities);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Subject subject = userRepository.findByUsername(username);
        if (subject == null) {
            throw new UsernameNotFoundException(username);
        }
        final Set<GrantedAuthority> authorities = new HashSet<>();
        return new UserDetailsImpl(subject.getId(), subject.getUsername(), subject.getPassword(), authorities);
    }

}
