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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Entity(name = "category_option")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    private int order = -1;
}
