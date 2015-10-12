package com.dazlyn.dpo.view;

import com.dazlyn.dpo.model.Category;
import com.dazlyn.dpo.model.CategoryManager;
import com.dazlyn.dpo.model.CategoryOption;
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
    private List<CategoryOption> genres;

    @Inject
    private GroupClassManager groupClassManager;

    @Inject
    private CategoryManager categoryManager;

    @PostConstruct
    public void init() {
        loadGroupClasses();
        loadGenres();
    }

    private void loadGroupClasses() {
        groupClasses = groupClassManager.findAllByStudio(getStudio());
    }

    private void loadGenres() {
        genres = categoryManager.findForCategory(getStudio(), Category.CLASS_GENRE);
    }

    public void archiveSelectedGroupClass() {
        // TODO Not only archive this class, but archive all the enrollment in this class and schedules as well.
    }

    public void prepareNewGroupClass() {
        newGroupClass = new GroupClass();
    }
}
