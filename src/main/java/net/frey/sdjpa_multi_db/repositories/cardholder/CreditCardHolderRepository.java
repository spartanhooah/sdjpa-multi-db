package net.frey.sdjpa_multi_db.repositories.cardholder;

import net.frey.sdjpa_multi_db.domain.cardholder.CreditCardHolder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CreditCardHolderRepository extends JpaRepository<CreditCardHolder, Long> {
}
