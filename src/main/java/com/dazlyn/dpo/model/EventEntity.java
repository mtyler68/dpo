package com.dazlyn.dpo.model;

import java.util.Date;
import java.util.List;

public class EventEntity {


    private EventEntity parent;

    private Schedule schedule;

    /**
     * When this is a parent event to recurring events, the recurring events can be retrieved here.
     */
    private List<EventEntity> children;

    /**
     * Specifies the Resource this Event belongs to.
     */
    
    private Date startDateTime;

    private Date endDateTime;

    private String timeZone;

    private char[] details;
}
