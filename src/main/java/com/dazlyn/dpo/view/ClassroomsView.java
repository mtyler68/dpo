package com.dazlyn.dpo.view;

import com.dazlyn.dpo.model.Classroom;
import com.dazlyn.dpo.model.ClassroomManager;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class ClassroomsView extends AbstractStudioView implements Serializable {

    @Inject
    private ClassroomManager roomManager;

    @Getter
    @Setter
    private Classroom selectedRoom;

    @Getter
    private List<Classroom> rooms;

    @PostConstruct
    public void init() {
        loadRooms();
        prepareNewClassroom();
    }

    private void loadRooms() {
        rooms = roomManager.findAll(getStudio());
    }

    public void prepareNewClassroom() {
        selectedRoom = new Classroom();
    }

    @Transactional
    public void saveClassroom() {
        if (selectedRoom.getUid() == null) {
            selectedRoom.setStudio(getStudio());
            roomManager.persist(selectedRoom);
            loadRooms();
        } else {
            roomManager.merge(selectedRoom);
        }
    }

    @Transactional
    public void archiveSelectedClassroom() {
        selectedRoom.setArchived(true);
        selectedRoom.setArchivedDate(new Date());
        roomManager.merge(selectedRoom);
        rooms.remove(selectedRoom);
    }
}
