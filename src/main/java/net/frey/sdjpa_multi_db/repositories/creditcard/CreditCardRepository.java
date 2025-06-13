package net.frey.sdjpa_multi_db.repositories.creditcard;

import net.frey.sdjpa_multi_db.domain.creditcard.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {}
