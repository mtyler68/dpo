package com.dazlyn.dpo.dao;

import com.dazlyn.dpo.dao.AbstractRepository;
import com.dazlyn.dpo.model.Course;
import com.dazlyn.dpo.model.StudioEntity;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class CourseRepository extends AbstractRepository<String, Course> {

    public CourseRepository() {
        super(String.class, Course.class);
    }

    public List<Course> findAllByStudio(StudioEntity studio) {
        return findAllByStudio(studio, false);
    }

    public List<Course> findAllByStudio(StudioEntity studio, boolean archived) {
        return getEntityManager().createNamedQuery("GroupClass.findAllByStudio", Course.class)
                .setParameter("studio", studio)
                .setParameter("archived", archived)
                .getResultList();
    }
}
