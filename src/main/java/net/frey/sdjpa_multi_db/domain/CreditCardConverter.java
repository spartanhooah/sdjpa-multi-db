package net.frey.sdjpa_multi_db.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_multi_db.services.EncryptionService;
import org.springframework.stereotype.Component;

@Converter
@Component
@RequiredArgsConstructor
public class CreditCardConverter implements AttributeConverter<String, String> {
    private final EncryptionService encryptionService;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return encryptionService.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return encryptionService.decrypt(dbData);
    }
}
