package com.example.springliquidbase.infrastructure;

import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.Instant;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseModel {

    @Id
    protected long id;
    @Version
    protected long version;
    @WhenCreated
    protected Instant createdOn;
    @WhenModified
    protected Instant modifiedOn;
}
