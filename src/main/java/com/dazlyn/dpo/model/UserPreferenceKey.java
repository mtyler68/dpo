package com.dazlyn.dpo.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserPreferenceKey {

    TABLE_RECORD_COUNT(Integer.class);

    private final Class classType;
}
