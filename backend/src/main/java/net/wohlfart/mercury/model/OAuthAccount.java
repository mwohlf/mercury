package net.wohlfart.mercury.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "providerName", "providerUid"})
@Entity(name = "oauthAccount")
@Table(name = "OAUTH_ACCOUNT", schema = "MC")
public class OAuthAccount implements Serializable {

    @Id
    @GenericGenerator(name = "sequenceGenerator",
            strategy = "org.hibernate.id.enhanced.TableGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter( name = "segment_value", value = "OAUTH_ACCOUNT"),
                    @org.hibernate.annotations.Parameter( name = "initial_value", value = "10"),
                    @org.hibernate.annotations.Parameter( name = "table_name", value = "SEQUENCES")
            })
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name="USER_ID", nullable=false)
    private User owner;

    @Column(name = "PROVIDER_NAME")
    private String providerName;

    @Column(name = "PROVIDER_UID")
    private String providerUid;

    @OneToMany(mappedBy = "oauthAccount")
    private Set<OAuthToken> token = new HashSet<>();

    public void addToken(OAuthToken token) {
        this.token.add(token);
        token.setOauthAccount(this);
    }

}
