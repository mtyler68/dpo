package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person_group")
@Getter
@Setter
public class PersonGroupEntity extends AbstractStudioEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private GroupType type;

    @ManyToMany(
            targetEntity = Person.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "person_group_membership",
            joinColumns = @JoinColumn(name = "person_group_uid"),
            inverseJoinColumns = @JoinColumn(name = "person_uid")
    )
    private List<Person> members;
}
