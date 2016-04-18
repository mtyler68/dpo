package com.dazlyn.dpo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Entity
@Table(name = "studio")
@NamedQueries({
    @NamedQuery(name = "Studio.findAll",
            query = "SELECT s FROM Studio s"),
    @NamedQuery(name = "Studio.findByRealmId",
            query = "SELECT s FROM Studio s WHERE s.realmId = :realmId")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Studio extends AbstractEntity implements Serializable {

    private String name;

    private String code;

    @Column(name = "realm_id", length = 36)
    private String realmId;

    @Enumerated(EnumType.STRING)
    private StudioStatus status;
}
