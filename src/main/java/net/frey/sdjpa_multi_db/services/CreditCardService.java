package net.frey.sdjpa_multi_db.services;

import net.frey.sdjpa_multi_db.domain.creditcard.CreditCard;

/**
 * Created by jt on 7/1/22.
 */
public interface CreditCardService {

    CreditCard getCreditCardById(Long id);
}
