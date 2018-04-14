package net.wohlfart.mercury.repository;

import net.wohlfart.mercury.App;
import net.wohlfart.mercury.model.RemotePrincipal;
import net.wohlfart.mercury.model.Subject;
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

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@SqlGroup({
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:data/hsql/clear.sql",
            "classpath:data/hsql/init-user.sql"
    }),
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:data/hsql/clear.sql"
    })
})
@Transactional
@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
public class SubjectRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test(timeout = 2000)
    public void findByIdTest() {
        Subject fetchedSubject = userRepository.findOne(1L);
        assertNotNull("Subject shouldn't be NULL", fetchedSubject);
    }

    @Test(timeout = 5000)
    public void findByEmailTest() {
        Subject fetchedSubject = userRepository.findByEmail("test1@test.de");
        assertNotNull("Subject shouldn't be NULL", fetchedSubject);
        assertEquals("Should return appropriate user", 1L, fetchedSubject.getId().longValue());
    }

    @Test(timeout = 5000)
    public void findByUsernameTest() {
        Subject fetchedSubject = userRepository.findByUsername("test2");
        assertNotNull("Subject shouldn't be NULL", fetchedSubject);
        assertEquals("Should return appropriate user", 2L, fetchedSubject.getId().longValue());
    }

    @Test(timeout = 2000)
    public void existsTest() {
        assertTrue("Subject should exists", userRepository.exists(3L));
        assertFalse("Subject shouldn't exists", userRepository.exists(Long.MAX_VALUE));
    }

    @Test(timeout = 3000)
    public void countTest() {
        assertEquals("Should fetch 3 users", 9, userRepository.count());
    }

    @Test(timeout=5000)
    public void findAllTest() {
        List<Subject> fetchedSubjects = userRepository.findAll();

        assertNotNull("Fetched users shouldn't be NULL", fetchedSubjects);
        assertEquals("Should return appropriate amount of users", 9, fetchedSubjects.size());
        fetchedSubjects.forEach(user -> assertNotNull("Any of the users shouldn't be NULL", user));
    }

    @Test(timeout=5000)
    public void saveTest() {
        Subject subject = Subject.builder().email("test@test.de").username("test100").build();
        userRepository.save(subject);
        Subject fetchedSubject = userRepository.findByUsername(subject.getUsername());
        assertNotNull("Subject shouldn't be NULL", fetchedSubject);
        assertEquals("Subject should have appropriate role", subject.getRoles(), fetchedSubject.getRoles());
        assertEquals("Subject should have appropriate name", subject.getUsername(), fetchedSubject.getUsername());
    }

    @Test(timeout=5000)
    public void saveWithAccountTest() {
        Subject subject = Subject.builder().email("testacc@test.de").username("test100acc").build();
        RemotePrincipal account = RemotePrincipal.builder().providerName("facebook").remoteUserId("fid").build();

        subject.setRemotePrincipals(new HashSet<>());
        subject.addRemotePrincipal(account);

        userRepository.save(subject);
        Subject fetchedSubject = userRepository.findByUsername(subject.getUsername());
        assertNotNull("Subject shouldn't be NULL", fetchedSubject);
        assertEquals("Subject should have appropriate role", subject.getRoles(), fetchedSubject.getRoles());
        assertEquals("Subject should have appropriate name", subject.getUsername(), fetchedSubject.getUsername());
    }

    @Test(timeout=5000)
    public void changeUsernameTest() {
        String newName = "another random name";
        Subject subject = userRepository.findOne(5L);
        String oldName = subject.getUsername();
        subject.setUsername(newName);
        userRepository.saveAndFlush(subject);
        Subject changedSubject = userRepository.findOne(5L);

        assertNotNull("Changed subject shouldn't be NULL", changedSubject);
        assertEquals("Should return appropriate subject", subject, changedSubject);
        assertEquals("Should return subject with appropriate name", subject.getUsername(), changedSubject.getUsername());
        assertNotEquals("Should return subject with appropriate name", subject.getUsername(), oldName);
    }

    @Test(timeout=5000)
    public void changeEmailTest() {
        String newEmail = "another@email.net";
        Subject subject = userRepository.findOne(6L);
        String oldEmail = subject.getEmail();
        subject.setEmail(newEmail);
        userRepository.saveAndFlush(subject);
        Subject changedSubject = userRepository.findOne(6L);

        assertNotNull("Changed subject shouldn't be NULL", changedSubject);
        assertEquals("Should return appropriate subject", subject, changedSubject);
        assertEquals("Should return subject with appropriate email", subject.getEmail(), changedSubject.getEmail());
        assertNotEquals("Should return subject with appropriate email", subject.getEmail(), oldEmail);
    }

    @Test(timeout=5000)
    public void changePasswordTest() {
        Subject subject = userRepository.findOne(2L);
        subject.setPassword("another-password");
        Subject changedSubject = userRepository.findOne(2L);

        assertNotNull("Changed subject shouldn't be NULL", changedSubject);
        assertEquals("Should return appropriate subject", subject, changedSubject);
        assertEquals("Should return subject with appropriate password",
                subject.getPassword(), changedSubject.getPassword());
    }

    @Test(timeout=5000)
    public void deleteTest() {
        userRepository.delete(9L);
        assertFalse("Subject with id 1001 should not exists", userRepository.exists(1001L));
    }

}
