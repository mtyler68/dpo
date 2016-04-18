package com.dazlyn.dpo.view;

import com.dazlyn.dpo.model.CategoryType;
import com.dazlyn.dpo.dao.CategoryRepository;
import com.dazlyn.dpo.model.Category;
import com.dazlyn.dpo.model.Classroom;
import com.dazlyn.dpo.model.Course;
import com.dazlyn.dpo.dao.CourseRepository;
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
public class GroupClassesView extends AbstractStudioView implements Serializable {

    @Getter
    private List<Course> groupClasses;

    @Getter
    @Setter
    private List<Course> filteredGroupClasses;

    @Getter
    @Setter
    private Course selectedGroupClass;

    @Getter
    @Setter
    private Course newGroupClass;

    @Getter
    private String classRoomString;

    @Getter
    @Setter
    private Classroom classRoom;

    @Inject
    private CourseRepository groupClassManager;

    @Inject
    private CategoryRepository categoryManager;

    @PostConstruct
    public void init() {
        loadGroupClasses();
        newGroupClass = new Course();
    }

    private void loadGroupClasses() {
        groupClasses = groupClassManager.findAllByStudio(getStudio());
    }

    public void archiveSelectedGroupClass() {
        // TODO Not only archive this class, but archive all the enrollment in this class and schedules as well.
    }

    public void prepareNewGroupClass() {
        newGroupClass = new Course();
    }

    public List<Category> categoryOptions(String category) {
        return categoryManager.findAllForType(getStudio(), CategoryType.valueOf(category));
    }

    public void setClassRoom(String value) {
        this.classRoomString = value;
    }
}
