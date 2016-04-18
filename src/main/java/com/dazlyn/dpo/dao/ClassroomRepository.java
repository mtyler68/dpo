package com.dazlyn.dpo.dao;

import com.dazlyn.dpo.dao.AbstractStudioRepository;
import com.dazlyn.dpo.model.Classroom;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ClassroomRepository extends AbstractStudioRepository<String, Classroom> {

    public ClassroomRepository() {
        super(String.class, Classroom.class);
    }

}
