package com.dazlyn.dpo.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.picketlink.idm.model.annotation.AttributeProperty;
import org.picketlink.idm.model.basic.Group;

@Getter
@Setter
public class GroupClass extends Group implements Serializable {

    @AttributeProperty(managed = true)
    private String title;

    public GroupClass() {
    }

    public GroupClass(String name) {
        super(name);
    }
}
