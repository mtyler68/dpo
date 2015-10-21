package com.dazlyn.dpo.controller;

import com.dazlyn.dpo.model.Person;
import com.dazlyn.dpo.dao.PersonRepository;
import com.dazlyn.dpo.model.Studio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class StudentsController implements Serializable {

    @Inject
    @Named("studio")
    private Studio studio;

    @Inject
    private PersonRepository personManager;

    @Getter
    private List<Person> students;

    @Getter
    @Setter
    private List<Person> filteredStudents;

    @PostConstruct
    public void init() {
        students = personManager.findStudentsByStudio(studio);
    }

}
