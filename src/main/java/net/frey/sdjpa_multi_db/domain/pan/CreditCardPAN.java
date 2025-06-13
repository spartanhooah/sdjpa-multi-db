package net.frey.sdjpa_multi_db.domain.pan;

import jakarta.persistence.*;
import lombok.*;
import net.frey.sdjpa_multi_db.domain.CreditCardConverter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardPAN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CreditCardConverter.class)
    private String creditCardNumber;
}
