package com.academic.portal.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        return gender != null ? gender.getId() : null;
    }

    @Override
    public Gender convertToEntityAttribute(Integer value) {
        return value != null ? Gender.fromId(value) : null;
    }
}
