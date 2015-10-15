package com.dazlyn.dpo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity implements Serializable {

    @Id
    @Column(length = 36, unique = true, updatable = false)
    private String uid;

    @Version
    @Column(name = "OPTLOCK")
    private Integer version;
}
