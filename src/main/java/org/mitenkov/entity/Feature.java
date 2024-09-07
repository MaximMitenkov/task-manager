package org.mitenkov.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("FEATURE")
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
public class Feature extends Task {

    @Override
    public Class<? extends Task> getTaskClass() {
        return Feature.class;
    }
}
