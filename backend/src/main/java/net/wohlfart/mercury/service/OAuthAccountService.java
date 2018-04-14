package net.wohlfart.mercury.service;

import net.wohlfart.mercury.model.RemotePrincipal;
import net.wohlfart.mercury.repository.OAuthAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthAccountService {

    @Autowired
    private OAuthAccountRepository oauthAccountRepository;

    // @Autowired
    // private AuthDocumentRepository authDocumentRepository;


    public RemotePrincipal findByRemoteUserId(String provider, String uid) {
        return oauthAccountRepository.findByProviderNameAndRemoteUserId(provider, uid);
    }

    public RemotePrincipal create(RemotePrincipal oauthAccount) {
        // todo: check for update, id
        return oauthAccountRepository.save(oauthAccount);
    }

    /*
    public AuthContext create(AuthContext authContext) {
        return authDocumentRepository.create(authContext);
    }
    */

    public RemotePrincipal save(RemotePrincipal oauthAccount) {
        return oauthAccountRepository.save(oauthAccount);
    }

}
