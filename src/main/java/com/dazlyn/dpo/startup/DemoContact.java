package com.dazlyn.dpo.startup;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class DemoContact {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String streetAddress;

    private String city;

    private String state;

    private String zipCode;

    private String country;
}
