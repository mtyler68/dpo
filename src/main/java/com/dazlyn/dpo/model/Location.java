package com.dazlyn.dpo.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "location")
@Data
public class Location extends AbstractArchivableStudioEntity implements Serializable {

    private String name;
}
