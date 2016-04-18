package com.dazlyn.dpo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "studio_settings")
@NamedQueries({
    @NamedQuery(name = "StudioSettings.findForStudio",
            query = "SELECT ss FROM StudioSettings ss WHERE ss.studio = :studio")
})
@Data
@NoArgsConstructor
public class StudioSettings extends AbstractStudioEntity implements Serializable {

    @Column(name = "schedule_scroll_time")
    private String scheduleScrollTime = "09:00:00";
}
