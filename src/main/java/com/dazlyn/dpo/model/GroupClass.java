package com.dazlyn.dpo.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
@Table(name = "group_class")
@NamedQueries({
    @NamedQuery(name = "GroupClass.findAllByStudio",
            query = "SELECT gc FROM GroupClass gc WHERE gc.studio = :studio AND gc.archived = :archived")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupClass extends AbstractArchivableStudioEntity implements Serializable {

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_uid")
    private CategoryOption genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_level_uid")
    private CategoryOption classLevel;
}
