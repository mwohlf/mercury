package net.wohlfart.mercury.model;


import lombok.*;
import org.hibernate.envers.Audited;

@Data
@Builder
@Audited // doesnt work
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"code", "message", "details"})
public class AlertMessage {

    Integer code;

    String message;

    String details;

}
