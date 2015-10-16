package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Entity
@Table(name = "course")
@NamedQueries({
    @NamedQuery(name = "GroupClass.findAllByStudio",
            query = "SELECT gc FROM Course c WHERE c.studio = :studio AND c.archived = :archived")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Course extends AbstractStudioEntity implements Serializable {

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_uid")
    private CategoryEntity genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_level_uid")
    private CategoryEntity classLevel;

    @ManyToMany(
            targetEntity = Person.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "enrollment",
            joinColumns = @JoinColumn(name = "group_class_uid"),
            inverseJoinColumns = @JoinColumn(name = "person_uid")
    )
    private List<Person> students;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classroom_uid")
    private Classroom classroom;
}
