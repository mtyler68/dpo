package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Entity(name = "family")
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Family implements Serializable {

    @Id
    @Column(length = 36)
    private String uid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "main_person_uid")
    private Person mainPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_uid")
    private Studio studio;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_uid")
    private List<Person> members = new ArrayList<>();
}
