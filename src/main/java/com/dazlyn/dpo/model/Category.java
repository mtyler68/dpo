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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

@Entity
@Table(name = "category")
@NamedQueries({
    @NamedQuery(name = "Category.findAllForType",
            query = "SELECT c FROM Category c "
                    + "WHERE c.studio = :studio "
                    + "AND c.type = :category "
                    + "AND c.archived = :archived "
                    + "ORDER BY c.sortOrder ASC"),
    @NamedQuery(name = "Category.findForValue",
            query = "SELECT c FROM Category c "
                    + "WHERE c.studio = :studio "
                    + "AND c.type = :category "
                    + "AND c.value = :value")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Category extends AbstractStudioEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CategoryType type;

    @Column(length = 100)
    private String value;

    @Column(name = "sort_order")
    private int sortOrder = -1;
}
