package net.wohlfart.mercury.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "role")
@Table(name = "ROLE", schema = "MC")
public class Role implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "PARENT_ID")
    private Role parent;

    @OneToMany(mappedBy = "parent")
    private Set<Role> children = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "PERMISSION_ROLE", schema = "MC",
            joinColumns = @JoinColumn(name = "PERMISSION_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<Permission> permission = new HashSet<>();

}
