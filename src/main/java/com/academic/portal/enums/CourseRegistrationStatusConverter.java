package com.academic.portal.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CourseRegistrationStatusConverter implements AttributeConverter<CourseRegistrationStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CourseRegistrationStatus status) {
        return status != null ? status.getId() : null;
    }

    @Override
    public CourseRegistrationStatus convertToEntityAttribute(Integer integer) {
        return integer != null ? CourseRegistrationStatus.fromId(integer) : null;
    }
}
