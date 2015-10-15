package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * Represents an event.
 */
@Entity(name = "schedule_event")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleEvent extends AbstractStudioEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", length = 30, nullable = false)
    private EventType eventType;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "student_uid", nullable = true)
    private Person student;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "group_class_uid", nullable = true)
    private GroupClass GroupClass;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "person_uid", nullable = true)
    private Person person;

    @Column(name = "all_day_event")
    private boolean allDayEvent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date_time", nullable = true)
    private Date startDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date_time", nullable = true)
    private Date endDateTime;

    private String title;

    private String description;
}
