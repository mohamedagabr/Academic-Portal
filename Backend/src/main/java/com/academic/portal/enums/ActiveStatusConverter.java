package com.academic.portal.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ActiveStatusConverter implements AttributeConverter<ActiveStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ActiveStatus status) {
        return status != null ? status.getId() : null;
    }

    @Override
    public ActiveStatus convertToEntityAttribute(Integer value) {
        return value != null ? ActiveStatus.fromId(value) : null;
    }
}