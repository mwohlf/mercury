package net.wohlfart.mercury.repository;

import net.wohlfart.mercury.App;
import net.wohlfart.mercury.model.OAuthAccount;
import net.wohlfart.mercury.model.OAuthToken;
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

import java.util.HashSet;

@SqlGroup({
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:data/hsql/clear.sql",
        "classpath:data/hsql/init-oauth-account.sql"
    }),
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:data/hsql/clear.sql")
})
@Transactional
@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
public class OAuthAccountRepositoryTest {

    @Autowired
    private OAuthAccountRepository oauthAccountRepository;

    @Test(timeout=5000)
    public void createOauthTest() {
        User user = User.builder().email("oauthtest1@test.de").username("oauthtest1").build();
        OAuthAccount oauthAccount = OAuthAccount.builder().owner(user).providerName("google").providerUid("one").build();
        oauthAccountRepository.save(oauthAccount);
    }

    @Test(timeout=5000)
    public void createOauthTokenTest() {
        User user = User.builder().email("oauthtest2@test.de").username("oauthtest2").build();
        OAuthToken token1 = OAuthToken.builder().key("key1").value("value1").build();
        OAuthToken token2 = OAuthToken.builder().key("key2").value("value2").build();
        OAuthAccount oauthAccount = OAuthAccount.builder().providerName("google").providerUid("one").token(new HashSet<>()).build();
        oauthAccount.setOwner(user);
        oauthAccount.addToken(token1);
        oauthAccount.addToken(token2);
        oauthAccountRepository.save(oauthAccount);
    }

}