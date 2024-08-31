package org.mitenkov.enums;

import lombok.Getter;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;

@Getter
public enum FilterType {
    BUG(Bug.class),
    FEATURE(Feature.class),;

    private final Class<? extends Task> filterClass;

    FilterType(Class<? extends Task> paramClass) {
        this.filterClass = paramClass;
    }
}
