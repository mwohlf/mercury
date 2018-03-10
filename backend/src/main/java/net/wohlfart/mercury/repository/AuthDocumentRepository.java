package net.wohlfart.mercury.repository;

import net.wohlfart.mercury.model.OAuthAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthDocumentRepository extends MongoRepository<OAuthAccount, Long> {

    OAuthAccount findByProviderNameAndProviderUid(String provider, String uid);

}
