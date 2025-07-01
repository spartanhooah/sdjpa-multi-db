package net.frey.sdjpa_multi_db.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_multi_db.domain.cardholder.CreditCardHolder;
import net.frey.sdjpa_multi_db.domain.creditcard.CreditCard;
import net.frey.sdjpa_multi_db.domain.pan.CreditCardPAN;
import net.frey.sdjpa_multi_db.repositories.cardholder.CreditCardHolderRepository;
import net.frey.sdjpa_multi_db.repositories.creditcard.CreditCardRepository;
import net.frey.sdjpa_multi_db.repositories.pan.CreditCardPANRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardService {
    private final CreditCardHolderRepository cardHolderRepository;
    private final CreditCardRepository cardRepository;
    private final CreditCardPANRepository panRepository;

    @Transactional
    public CreditCard saveCreditCard(CreditCard card) {
        var savedCard = cardRepository.save(card);
        savedCard.setFirstName(card.getFirstName());
        savedCard.setLastName(card.getLastName());
        savedCard.setZipCode(card.getZipCode());
        savedCard.setCreditCardNumber(card.getCreditCardNumber());

        cardHolderRepository.save(CreditCardHolder.builder()
                .creditCardId(savedCard.getId())
                .firstName(savedCard.getFirstName())
                .lastName(savedCard.getLastName())
                .zipCode(savedCard.getZipCode())
                .build());

        panRepository.save(CreditCardPAN.builder()
                .creditCardId(savedCard.getId())
                .creditCardNumber(savedCard.getCreditCardNumber())
                .build());

        return savedCard;
    }

    @Transactional
    CreditCard getCreditCardById(Long id) {
        var card = cardRepository.findById(id).orElseThrow();
        var cardHolder = cardHolderRepository.findByCreditCardId(id).orElseThrow();
        var cardPAN = panRepository.findByCreditCardId(id).orElseThrow();

        card.setFirstName(cardHolder.getFirstName());
        card.setLastName(cardHolder.getLastName());
        card.setZipCode(cardHolder.getZipCode());
        card.setCreditCardNumber(cardPAN.getCreditCardNumber());

        return card;
    }
}
