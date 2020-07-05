package com.zxyroy.fx.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BasicEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private boolean deleted = false;

    @Getter
    private final Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Getter
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    @PreUpdate
    public void setLastUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

}