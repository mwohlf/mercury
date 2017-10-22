package net.wohlfart.mercury.service;

import net.wohlfart.mercury.model.User;
import net.wohlfart.mercury.security.UserNotFoundException;
import net.wohlfart.mercury.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Returns all users
     * @return List of users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Returns user with given id
     * @param id to look for
     * @return user with given id
     * @throws UserNotFoundException if user with given id does not exists
     */
    public User findById(Long id) {
        if(userRepository.exists(id)) {
            return userRepository.findOne(id);
        } else {
            throw new UserNotFoundException(null);
        }
    }

    /**
     * Returns user with given email
     * @param email to look for
     * @return user with given email
     * @throws UserNotFoundException if user with given email does not exists
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Returns user with given name
     * @param name to look for
     * @return name with given email
     * @throws UserNotFoundException if user with given name does not exists
     */
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    /**
     * Adds or updates user.
     * If user with following id already exists it will be updated elsewhere added as the new one.
     * @param user to add or update
     * @return Added or updated user
     */
    public User save(User user) {
        if(!userRepository.exists(user.getId())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    /**
     * Deletes user by given id
     * @param id to look for
     * @throws UserNotFoundException if user with given id does not exists
     */
    public void delete(Long id) {
        if(userRepository.exists(id)) {
            userRepository.delete(id);
        } else {
            throw new UserNotFoundException(null);
        }
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
