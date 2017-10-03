package net.wohlfart.mercury.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;

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
@Entity(name = "user")
@Table(name = "USER", schema = "MC")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    private String password;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(name = "MEMBERSHIP", schema = "MC",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private Set<Role> roles = new HashSet<>();

}
