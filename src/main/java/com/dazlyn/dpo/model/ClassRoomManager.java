package com.dazlyn.dpo.model;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ClassRoomManager extends AbstractEntityManager<String, ClassRoom> {

    public ClassRoomManager() {
        super(String.class, ClassRoom.class);
    }

}
