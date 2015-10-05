package com.dazlyn.dpo.model;

import java.io.Serializable;
import org.picketlink.idm.model.basic.User;

public class Person extends User implements Serializable {

    public Person() {
    }

    public Person(String loginName) {
        super(loginName);
    }

}
