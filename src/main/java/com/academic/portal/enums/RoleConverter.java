package com.academic.portal.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public Role convertToEntityAttribute(Integer dbData) {
        return Role.fromId(dbData);
    }
}
