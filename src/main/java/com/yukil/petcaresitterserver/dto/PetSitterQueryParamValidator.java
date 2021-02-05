package com.yukil.petcaresitterserver.dto;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static org.springframework.util.StringUtils.hasText;

@Component
public class PetSitterQueryParamValidator {
    public void validate(PetSitterQueryParam param, Errors errors) {
        try {
            if (LocalTime.parse(param.getFromTime()).isAfter(LocalTime.parse(param.getToTime()))) {
                errors.rejectValue("fromTime", "wrong range", "time range is wrong");
            }
        } catch (DateTimeParseException dateTimeParseException) {
            errors.rejectValue("fromTime", "wrongFormat", "wrong time format");
        }
    }
}
