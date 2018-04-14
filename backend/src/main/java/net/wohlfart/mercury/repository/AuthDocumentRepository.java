package net.wohlfart.mercury.repository;

import net.wohlfart.mercury.security.oauth.AuthContext;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthDocumentRepository extends MongoRepository<AuthContext, Long> {

   // RemotePrincipal findByProviderNameAndProviderUid(String provider, String uid);

}
