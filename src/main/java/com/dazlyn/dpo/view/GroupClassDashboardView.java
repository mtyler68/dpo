package com.dazlyn.dpo.view;

import com.dazlyn.dpo.model.GroupClass;
import com.dazlyn.dpo.model.GroupClassManager;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Named
@ViewScoped
@Slf4j
public class GroupClassDashboardView extends AbstractStudioView implements Serializable {

    @Getter
    private GroupClass groupClass;

    @Getter
    private String groupClassUid;

    @Inject
    private GroupClassManager groupClassManager;

    public void setGroupClassUid(String uid) {
        this.groupClassUid = uid;
        groupClass = groupClassManager.find(uid);
    }
}
