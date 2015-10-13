package com.dazlyn.dpo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {

    BILLING_SCHEDULE("Billing Schedule"),
    CHARGE_CATEGORY("Charge Category"),
    CLASS_GENRE("Class Genre"),
    CLASS_LEVEL("Class Level"),
    CLASS_LOCATION("Class Location"),
    CLASS_PROGRAM("Class Program"),
    PAYMENT_CYCLE("Payment Cycle"),
    PAYMENT_METHOD("Payment Method"),
    PAYMENT_SCHEDULE("Payment Schedule");

    @Getter
    private final String label;
}
