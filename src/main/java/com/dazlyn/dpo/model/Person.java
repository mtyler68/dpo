package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;

@Entity
@Table(name = "person")
@NamedQueries({
    @NamedQuery(name = "Person.findStudentsByStudio",
            query = "SELECT p FROM Person p WHERE p.studio = :studio AND p.typeStudent = TRUE")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"mainPerson"})
public class Person extends AbstractStudioEntity implements Serializable {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "main_person_uid", insertable = false, updatable = false)
    private Person mainPerson;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_person_uid")
    private List<Person> members = new ArrayList<>();

    /**
     * When true, this person is a student and will appear in the list of students.
     */
    @Column(name = "type_student")
    private boolean typeStudent;

    /**
     * When true, this person is an employee and will show up in employee related lists, which can include instructors.
     */
    @Column(name = "type_employee")
    private boolean typeEmployee;

    @ManyToMany(
            targetEntity = Course.class,
            mappedBy = "students",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    private List<Course> groupClasses;
}
