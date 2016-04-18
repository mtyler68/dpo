package com.dazlyn.dpo.view;

import com.dazlyn.dpo.model.EventType;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;
import org.joda.time.LocalDateTime;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;

@Named
@ViewScoped
public class ScheduleView extends AbstractStudioView implements Serializable {

    @Getter
    private ScheduleModel model;

    @Getter
    @Setter
    private org.primefaces.model.ScheduleEvent selectedEvent;

    @Getter
    private ScheduleDialogModel dialogModel;

    @PostConstruct
    public void init() {
        loadScheduleModel();
    }

    private void loadScheduleModel() {
        model = new LazyScheduleModel() {

            @Override
            public void loadEvents(Date start, Date end) {

            }

        };
    }

    public void onDateSelect(SelectEvent evt) {
        Date startDate = (Date) evt.getObject();
        LocalDateTime endDateTime = LocalDateTime.fromDateFields(startDate)
                .plusMinutes(30);

        dialogModel = ScheduleDialogModel.builder()
                .title("New Event")
                .eventType(EventType.GLOBAL_EVENT)
                .startDateTime(startDate)
                .endDateTime(endDateTime.toDate())
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ScheduleDialogModel {

        private String title;

        private EventType eventType;

        private Date startDateTime;

        private Date endDateTime;

        private boolean allDayEvent;

    }
}
