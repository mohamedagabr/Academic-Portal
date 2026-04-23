package com.academic.portal.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IdentityTypeConverter implements AttributeConverter<IdentityType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(IdentityType identityType) {
        return identityType == null ? null : identityType.getId();
    }

    @Override
    public IdentityType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : IdentityType.fromId(integer);
    }
}
