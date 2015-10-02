package com.dazlyn.dpo.web.security;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.picketlink.idm.model.annotation.AttributeProperty;
import org.picketlink.idm.model.basic.Realm;

@Getter
@Setter
public class Studio extends Realm implements Serializable {

    @AttributeProperty(managed = true)
    private String businessName;

    public Studio() {
    }

    public Studio(String name) {
        super(name);
    }

}
