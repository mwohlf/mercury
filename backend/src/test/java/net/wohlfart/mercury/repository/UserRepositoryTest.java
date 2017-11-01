package net.wohlfart.mercury.repository;

import net.wohlfart.mercury.App;
import net.wohlfart.mercury.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@SqlGroup({
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:data/hsql/init-user.sql"
    }),
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:data/hsql/clear.sql")
})
@Transactional
@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test(timeout = 2000)
    public void findByIdTest() {
        User fetchedUser = userRepository.findOne(1L);
        assertNotNull("User shouldn't be NULL", fetchedUser);
    }

    @Test(timeout = 5000)
    public void findByEmailTest() {
        User fetchedUser = userRepository.findByEmail("test1@test.de");
        assertNotNull("User shouldn't be NULL", fetchedUser);
        assertEquals("Should return appropriate user", 1L, fetchedUser.getId().longValue());
    }

    @Test(timeout = 5000)
    public void findByNameTest() {
        User fetchedUser = userRepository.findByName("test2");
        assertNotNull("User shouldn't be NULL", fetchedUser);
        assertEquals("Should return appropriate user", 2L, fetchedUser.getId().longValue());
    }

    @Test(timeout = 2000)
    public void existsTest() {
        assertTrue("User should exists", userRepository.exists(3L));
        assertFalse("User shouldn't exists", userRepository.exists(Long.MAX_VALUE));
    }

    @Test(timeout = 3000)
    public void countTest() {
        assertEquals("Should fetch 3 users", 9, userRepository.count());
    }

    @Test(timeout=5000)
    public void findAllTest() {
        List<User> fetchedUsers = userRepository.findAll();

        assertNotNull("Fetched users shouldn't be NULL", fetchedUsers);
        assertEquals("Should return appropriate amount of users", 9, fetchedUsers.size());
        fetchedUsers.forEach(user -> assertNotNull("Any of the users shouldn't be NULL", user));
    }

    @Test(timeout=5000)
    public void saveTest() {
        User user = User.builder().email("test@test.de").name("test100").build();
        userRepository.save(user);
        User fetchedUser = userRepository.findByName(user.getName());
        assertNotNull("User shouldn't be NULL", fetchedUser);
        assertEquals("User should have appropriate role", user.getRoles(), fetchedUser.getRoles());
        assertEquals("User should have appropriate name", user.getName(), fetchedUser.getName());
    }

    @Test(timeout=5000)
    public void changeUsernameTest() {
        String newName = "another random name";
        User user = userRepository.findOne(5L);
        String oldName = user.getName();
        user.setName(newName);
        userRepository.saveAndFlush(user);
        User changedUser = userRepository.findOne(5L);

        assertNotNull("Changed user shouldn't be NULL", changedUser);
        assertEquals("Should return appropriate user", user, changedUser);
        assertEquals("Should return user with appropriate name", user.getName(), changedUser.getName());
        assertNotEquals("Should return user with appropriate name", user.getName(), oldName);
    }

    @Test(timeout=5000)
    public void changeEmailTest() {
        String newEmail = "another@email.net";
        User user = userRepository.findOne(6L);
        String oldEmail = user.getEmail();
        user.setEmail(newEmail);
        userRepository.saveAndFlush(user);
        User changedUser = userRepository.findOne(6L);

        assertNotNull("Changed user shouldn't be NULL", changedUser);
        assertEquals("Should return appropriate user", user, changedUser);
        assertEquals("Should return user with appropriate email", user.getEmail(), changedUser.getEmail());
        assertNotEquals("Should return user with appropriate email", user.getEmail(), oldEmail);
    }

    @Test(timeout=5000)
    public void changePasswordTest() {
        User user = userRepository.findOne(2L);
        user.setPassword("another-password");
        User changedUser = userRepository.findOne(2L);

        assertNotNull("Changed user shouldn't be NULL", changedUser);
        assertEquals("Should return appropriate user", user, changedUser);
        assertEquals("Should return user with appropriate password",
                user.getPassword(), changedUser.getPassword());
    }

    @Test(timeout=5000)
    public void deleteTest() {
        userRepository.delete(9L);
        assertFalse("User with id 1001 should not exists", userRepository.exists(1001L));
    }

}