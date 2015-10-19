package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;

@Entity(name = "person")
@NamedQueries({
    @NamedQuery(name = "Person.findByStudio",
            query = "SELECT p FROM person p WHERE p.studio = :studio"),
    @NamedQuery(name = "Person.findStudentsByStudio",
            query = "SELECT p FROM person p WHERE p.studio = :studio AND p.typeStudent = TRUE")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"family", "studio"})
public class Person implements Serializable {

    @Id
    @Column(length = 36)
    private String uid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_uid")
    private Studio studio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "family_uid", insertable = false, updatable = false)
    private Family family;

    /**
     * When true, this person is the guardian to the students related by family.
     */
    private boolean typeGuardian;

    /**
     * When true, this person is a student and will appear in the list of students.
     */
    private boolean typeStudent;

    /**
     * When true, this person is an employee and will show up in employee related lists, which can include instructors.
     */
    private boolean typeEmployee;

    @ManyToMany(
            targetEntity = Course.class,
            mappedBy = "students",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    private List<Course> groupClasses;
}
