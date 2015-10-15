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
@Table(name = "category_option")
@NamedQueries({
    @NamedQuery(name = "CategoryOption.findForCategory",
            query = "SELECT co FROM CategoryOption co WHERE co.studio = :studio AND co.category = :category AND co.archived = :archived ORDER BY co.sortOrder ASC"),
    @NamedQuery(name = "CategoryOption.findForOption",
            query = "SELECT co FROM CategoryOption co WHERE co.studio = :studio AND co.category = :category AND co.value = :value")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class CategoryOptionEntity extends AbstractStudioEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CategoryType type;

    @Column(length = 100)
    private String value;

    @Column(name = "sort_order")
    private int sortOrder = -1;
}
