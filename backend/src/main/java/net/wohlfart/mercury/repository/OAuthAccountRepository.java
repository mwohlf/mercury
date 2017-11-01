package net.wohlfart.mercury.repository;

import net.wohlfart.mercury.model.OAuthAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthAccountRepository extends JpaRepository<OAuthAccount, Long> {

    OAuthAccount findByProviderNameAndProviderUid(String provider, String uid);


}
