package net.wohlfart.mercury.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Data
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "providerName", "remoteUserId"})
@Entity(name = "remotePrincipal")
@Table(name = "REMOTE_PRINCIPAL", schema = "MC")
public class RemotePrincipal implements Serializable {

    @Id
    @GenericGenerator(name = "sequenceGenerator",
            strategy = "org.hibernate.id.enhanced.TableGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter( name = "segment_value", value = "REMOTE_PRINCIPAL"),
                    @org.hibernate.annotations.Parameter( name = "initial_value", value = "10"),
                    @org.hibernate.annotations.Parameter( name = "table_name", value = "SEQUENCES")
            })
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;


    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name="SUBJECT_ID", nullable=false)
    private Subject subject;

    @Column(name = "PROVIDER_NAME")
    private String providerName;

    @Column(name = "REMOTE_USER_ID")
    private String remoteUserId;

    @ElementCollection
    @CollectionTable(name = "PRINCIPAL_PROPERTIES", joinColumns = @JoinColumn(name = "REMOTE_PRINCIPAL_ID"))
    @MapKeyColumn(name = "NAME", length = 250)
    @Column(name = "VALUE", length = 250)
    @BatchSize(size = 20)
    private Map<String, String> token = new HashMap<>();

}
