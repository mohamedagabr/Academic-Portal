package com.academic.portal.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DeletedFlagConverter implements AttributeConverter<DeletedFlag, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DeletedFlag deleteFlag) {
        return deleteFlag == null ? null : deleteFlag.getId();
    }

    @Override
    public DeletedFlag convertToEntityAttribute(Integer value) {
        return value == null ? null : DeletedFlag.fromId(value);
    }
}
