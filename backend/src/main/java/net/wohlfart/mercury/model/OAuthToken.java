package net.wohlfart.mercury.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.io.Serializable;


@Data
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "key", "value"})
@Entity(name = "oauthToken")
@Table(name = "OAUTH_TOKEN", schema = "MC")
public class OAuthToken implements Serializable {

    @Id
    @GenericGenerator(name = "sequenceGenerator",
            strategy = "org.hibernate.id.enhanced.TableGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter( name = "segment_value", value = "OAUTH_TOKEN"),
                    @org.hibernate.annotations.Parameter( name = "initial_value", value = "10"),
                    @org.hibernate.annotations.Parameter( name = "table_name", value = "SEQUENCES")
            })
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;

    @Column(name = "OAUTH_ACCOUNT_ID")
    private OAuthAccount oauthAccount;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;

}
