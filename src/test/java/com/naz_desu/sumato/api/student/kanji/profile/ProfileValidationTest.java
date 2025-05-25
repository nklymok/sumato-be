package com.naz_desu.sumato.api.student.kanji.profile;

import com.naz_desu.sumato.api.student.kanji.profile.dto.UpdateUserProfileDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for nickname validation covering:
 * REQ-1: Allowed characters and length.
 * REQ-3: Reject special characters outside allowed set.
 */
class ProfileValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        validator = null;
    }

    @Test
    void validNameShouldHaveNoViolations() {
        UpdateUserProfileDTO dto = new UpdateUserProfileDTO("Name_123");
        Set<ConstraintViolation<UpdateUserProfileDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void nameTooShortShouldHaveViolation() {
        UpdateUserProfileDTO dto = new UpdateUserProfileDTO("Ab");
        Set<ConstraintViolation<UpdateUserProfileDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void nameWithInvalidCharsShouldHaveViolation() {
        UpdateUserProfileDTO dto = new UpdateUserProfileDTO("Bad@Name!");
        Set<ConstraintViolation<UpdateUserProfileDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }
}