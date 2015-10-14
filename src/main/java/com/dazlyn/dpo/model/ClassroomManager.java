package com.dazlyn.dpo.model;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ClassroomManager extends AbstractEntityManager<String, Classroom> {

    public ClassroomManager() {
        super(String.class, Classroom.class);
    }

}
