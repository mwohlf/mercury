package net.wohlfart.mercury.service;

import net.wohlfart.mercury.model.Subject;
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
    public List<Subject> findAll() {
        return userRepository.findAll();
    }

    /**
     * Returns user with given id
     * @param id to look for
     * @return user with given id
     * @throws UserNotFoundException if user with given id does not exists
     */
    public Subject findById(Long id) {
        if (userRepository.exists(id)) {
            return userRepository.findOne(id);
        } else {
            throw new UserNotFoundException("unknown id " + id);
        }
    }

    /**
     * Returns user with given email
     * @param email to look for
     * @return user with given email
     * @throws UserNotFoundException if user with given email does not exists
     */
    public Subject findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Returns user with given name
     * @param name to look for
     * @return name with given email
     * @throws UserNotFoundException if user with given name does not exists
     */
    public Subject findByName(String name) {
        return userRepository.findByUsername(name);
    }

    /**
     * Create subject.
     * If subject with following id already exists it will be updated elsewhere added as the new one.
     * @param subject to add
     * @return Added subject
     */
    public Subject create(Subject subject) {
        if (subject.getId() != null) {
            throw new IllegalArgumentException("userid must be null for create, was " + subject.getId());
        }
        if (subject.getPassword() != null) {
            subject.setPassword(passwordEncoder.encode(subject.getPassword()));
        }
        return userRepository.save(subject);
    }

    /**
     * Update subject.
     * If subject with following id already exists it will be updated elsewhere added as the new one.
     * @param subject to update
     * @return Updated subject
     */
    public Subject update(Subject subject) {
        if (subject.getId() == null) {
            throw new IllegalArgumentException("userid must be not be null for create, username was " + subject.getUsername());
        }
        if (subject.getPassword() != null) {
            subject.setPassword(passwordEncoder.encode(subject.getPassword()));
        }
        return userRepository.save(subject);
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
            throw new UserNotFoundException("can't delete");
        }
    }

    public Page<Subject> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
