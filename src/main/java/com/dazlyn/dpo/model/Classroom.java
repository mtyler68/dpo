package com.dazlyn.dpo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "classroom")
@Data
public class Classroom extends AbstractStudioEntity implements Serializable {

    @Column(length = 100)
    private String name;
}
