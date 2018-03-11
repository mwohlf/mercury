package net.wohlfart.mercury.service;

import net.wohlfart.mercury.model.OAuthAccount;
import net.wohlfart.mercury.repository.OAuthAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthAccountService {

    @Autowired
    private OAuthAccountRepository oauthAccountRepository;

    // @Autowired
    // private AuthDocumentRepository authDocumentRepository;


    public OAuthAccount findByProviderUid(String provider, String uid) {
        return oauthAccountRepository.findByProviderNameAndProviderUid(provider, uid);
    }

    public OAuthAccount create(OAuthAccount oauthAccount) {
        // todo: check for update, id
        return oauthAccountRepository.save(oauthAccount);
    }

    /*
    public AuthContext create(AuthContext authContext) {
        return authDocumentRepository.save(authContext);
    }
    */

    public OAuthAccount save(OAuthAccount oauthAccount) {
        return oauthAccountRepository.save(oauthAccount);
    }

}
