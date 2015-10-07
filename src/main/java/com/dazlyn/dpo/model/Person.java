package com.dazlyn.dpo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Entity(name = "person")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_uid")
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
}
