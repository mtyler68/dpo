package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Entity
@Table(name = "season")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SeasonEntity extends AbstractStudioEntity implements Serializable {

    @Column(length = 100)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_uid")
    private List<SeasonExcludeDateEntity> excludeDates;
}
