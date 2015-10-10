package com.dazlyn.dpo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;

@Entity
@Table(name = "category_option")
@NamedQueries({
    @NamedQuery(name = "CategoryOption.findForCategory",
            query = "SELECT co FROM CategoryOption co WHERE co.studio = :studio AND co.category = :category ORDER BY co.sortOrder ASC")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"studio"})
public class CategoryOption implements Serializable {

    @Id
    @Column(length = 36, unique = true, updatable = false)
    private String uid;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "studio_uid")
    private Studio studio;

    @Column(length = 100)
    private String value;

    @Column(name = "sort_order")
    private int sortOrder = -1;

}
