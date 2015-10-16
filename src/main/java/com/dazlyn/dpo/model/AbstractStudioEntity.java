package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractStudioEntity extends AbstractEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_uid", nullable = false)
    private StudioEntity studio;

    private boolean archived;

    @Column(name = "archived_timestamp", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date archivedTimestamp;
}
