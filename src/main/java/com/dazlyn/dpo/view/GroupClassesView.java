package com.dazlyn.dpo.view;

import com.dazlyn.dpo.model.CategoryType;
import com.dazlyn.dpo.dao.CategoryRepository;
import com.dazlyn.dpo.model.CategoryOptionEntity;
import com.dazlyn.dpo.model.Classroom;
import com.dazlyn.dpo.model.GroupClass;
import com.dazlyn.dpo.model.GroupClassManager;
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
    private List<GroupClass> groupClasses;

    @Getter
    @Setter
    private List<GroupClass> filteredGroupClasses;

    @Getter
    @Setter
    private GroupClass selectedGroupClass;

    @Getter
    @Setter
    private GroupClass newGroupClass;

    @Getter
    private String classRoomString;

    @Getter
    @Setter
    private Classroom classRoom;

    @Inject
    private GroupClassManager groupClassManager;

    @Inject
    private CategoryRepository categoryManager;

    @PostConstruct
    public void init() {
        loadGroupClasses();
        newGroupClass = new GroupClass();
    }

    private void loadGroupClasses() {
        groupClasses = groupClassManager.findAllByStudio(getStudio());
    }

    public void archiveSelectedGroupClass() {
        // TODO Not only archive this class, but archive all the enrollment in this class and schedules as well.
    }

    public void prepareNewGroupClass() {
        newGroupClass = new GroupClass();
    }

    public List<CategoryOptionEntity> categoryOptions(String category) {
        return categoryManager.findForCategory(getStudio(), CategoryType.valueOf(category));
    }

    public void setClassRoom(String value) {
        this.classRoomString = value;
    }
}
