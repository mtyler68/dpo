package com.dazlyn.dpo.view;

import com.dazlyn.dpo.model.ClassRoom;
import com.dazlyn.dpo.model.ClassRoomManager;
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
public class ClassRoomsView extends AbstractStudioView implements Serializable {

    @Inject
    private ClassRoomManager roomManager;

    @Getter
    @Setter
    private ClassRoom selectedRoom;

    @Getter
    private List<ClassRoom> rooms;

    @PostConstruct
    public void init() {
        loadRooms();
        prepareNewClassRoom();
    }

    private void loadRooms() {
        rooms = roomManager.findAll(getStudio());
    }

    public void prepareNewClassRoom() {
        selectedRoom = new ClassRoom();
    }

    @Transactional
    public void saveClassRoom() {
        if (selectedRoom.getUid() == null) {
            selectedRoom.setStudio(getStudio());
            roomManager.persist(selectedRoom);
            loadRooms();
        } else {
            roomManager.merge(selectedRoom);
        }
    }

    @Transactional
    public void archiveSelectedClassRoom() {
        selectedRoom.setArchived(true);
        selectedRoom.setArchivedDate(new Date());
        roomManager.merge(selectedRoom);
        rooms.remove(selectedRoom);
    }
}
