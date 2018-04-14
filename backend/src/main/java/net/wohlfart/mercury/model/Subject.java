package net.wohlfart.mercury.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "subject")
@Table(name = "SUBJECT", schema = "MC")
public class Subject implements Serializable {

    @Id
    @GenericGenerator(name = "sequenceGenerator",
            strategy = "org.hibernate.id.enhanced.TableGenerator",
            parameters = {
                @org.hibernate.annotations.Parameter( name = "segment_value", value = "USER"),
                @org.hibernate.annotations.Parameter( name = "initial_value", value = "10"),
                @org.hibernate.annotations.Parameter( name = "table_name", value = "SEQUENCES")
    })
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;

    @Column(unique = true, name="USERNAME")
    private String username;

    @Column(name="PASSWORD")
    private String password;

    @Email
    @Column(name="EMAIL")
    private String email;

    @ManyToMany
    @JoinTable(name = "MEMBERSHIP", schema = "MC",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "SUBJECT_ID")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "subject", cascade = {CascadeType.PERSIST})
    private Set<RemotePrincipal> remotePrincipals = new HashSet<>();

    public void addRemotePrincipal(RemotePrincipal account) {
        remotePrincipals.add(account);
        account.setSubject(this);
    }

}
