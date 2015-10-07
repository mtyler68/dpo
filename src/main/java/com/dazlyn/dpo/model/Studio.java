package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;

@Entity(name = "studio")
@NamedQueries({
    @NamedQuery(name = "Studio.findAll",
            query = "SELECT s FROM studio s"),
    @NamedQuery(name = "Studio.findByRealmId",
            query = "SELECT s FROM studio s WHERE s.realmId = :realmId"),
    @NamedQuery(name = "Studio.findByRealmIdWithFamilies",
            query = "SELECT s FROM studio s JOIN FETCH s.families f WHERE s.realmId = :realmId")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"families"})
public class Studio implements Serializable {

    @Id
    @Column(length = 36, unique = true)
    private String uid;

    private String name;

    private String code;

    @Column(name = "realm_id", length = 36)
    private String realmId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "studio")
    private List<Family> families = new ArrayList<>();

}
