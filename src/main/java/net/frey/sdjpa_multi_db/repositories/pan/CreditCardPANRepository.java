package net.frey.sdjpa_multi_db.repositories.pan;

import net.frey.sdjpa_multi_db.domain.pan.CreditCardPAN;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardPANRepository extends JpaRepository<CreditCardPAN, Long> {
}
