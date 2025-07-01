package net.frey.sdjpa_multi_db

import net.frey.sdjpa_multi_db.domain.creditcard.CreditCard
import net.frey.sdjpa_multi_db.services.CreditCardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SdjpaMultiDbApplicationTest extends Specification {
    @Autowired
    CreditCardService cardService

    def "Test save and get credit card"() {
        given:
        def card = CreditCard.builder()
            .firstName("John")
            .lastName("Thompson")
            .zipCode("12345")
            .creditCardNumber("1234567890")
            .cvv("123")
            .expirationDate("12/26")
            .build()

        when:
        def savedCard = cardService.saveCreditCard(card)

        then:
        savedCard
        savedCard.id
        savedCard.creditCardNumber

        and:
        when:
        def foundCard = cardService.getCreditCardById(savedCard.id)

        then:
        foundCard
        foundCard.id
        foundCard.creditCardNumber
    }
}
