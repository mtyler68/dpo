package com.dazlyn.dpo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Used for both person gender and course type genders.
 */
@RequiredArgsConstructor
public enum GenderType {

    FEMALE("Female"),
    MALE("Male"),
    COED("Co-ed");

    @Getter
    private final String label;
}
