package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Table(name = "studio")
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
public class StudioEntity extends AbstractEntity implements Serializable {

    private String name;

    private String code;

    @Column(name = "realm_id", length = 36)
    private String realmId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "studio")
    private List<Family> families = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StudioStatus status;
}
