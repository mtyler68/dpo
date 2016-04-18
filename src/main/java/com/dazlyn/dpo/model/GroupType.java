package com.dazlyn.dpo.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GroupType {

    FAMILY("Family"),
    STUDENT("Student"),
    EMPLOYEE("Employee"),
    INSTRUCTOR("Instructor"),
    LEAD("Lead"),
    OPPORTUNITY("Opportunity"),
    CONTACT("Contact");

    private final String label;
}
