package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractArchivableStudioEntity extends AbstractStudioEntity implements Serializable {

    private boolean archived;

    @Column(name = "archived_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date archivedDate;
}
