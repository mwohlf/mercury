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

import java.util.HashMap;

@SqlGroup({
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:data/hsql/clear.sql",
        "classpath:data/hsql/init-remote-principal.sql"
    }),
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:data/hsql/clear.sql")
})
@Transactional
@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
public class RemotePrincipalRepositoryTest {

    @Autowired
    private OAuthAccountRepository oauthAccountRepository;

    @Test(timeout=5000)
    public void createOauthTest() {
        Subject subject = Subject.builder().email("oauthtest1@test.de").username("oauthtest1").build();
        RemotePrincipal oauthAccount = RemotePrincipal.builder().subject(subject).providerName("google").remoteUserId("one").build();
        oauthAccountRepository.save(oauthAccount);
    }

    @Test(timeout=5000)
    public void createOauthTokenTest() {
        Subject subject = Subject.builder().email("oauthtest2@test.de").username("oauthtest2").build();
        RemotePrincipal oauthAccount = RemotePrincipal.builder().providerName("google").remoteUserId("one").token(new HashMap<>()).build();
        oauthAccount.setSubject(subject);
        oauthAccount.getToken().put("key1", "value1");
        oauthAccount.getToken().put("key2", "value2");
        oauthAccountRepository.save(oauthAccount);
    }

}
