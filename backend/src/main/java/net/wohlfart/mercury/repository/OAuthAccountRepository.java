package net.wohlfart.mercury.repository;

import net.wohlfart.mercury.model.RemotePrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthAccountRepository extends JpaRepository<RemotePrincipal, Long> {

    RemotePrincipal findByProviderNameAndRemoteUserId(String provider, String uid);

}
