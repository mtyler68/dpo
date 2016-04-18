package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "season_exclude_date")
@Data
public class SeasonExcludeDateEntity extends AbstractEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "season_uid", insertable = false, updatable = false)
    private SeasonEntity season;

    @Temporal(TemporalType.DATE)
    @Column(name = "target_date", nullable = false)
    private Date targetDate;
}
