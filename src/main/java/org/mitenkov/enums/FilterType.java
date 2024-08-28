package org.mitenkov.enums;

import lombok.Getter;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;

@Getter
public enum FilterType {
    BUG(Bug.class),
    FEATURE(Feature.class),;

    private final Class<?> filterClass;

    FilterType(Class<?> paramClass) {
        this.filterClass = paramClass;
    }
}
