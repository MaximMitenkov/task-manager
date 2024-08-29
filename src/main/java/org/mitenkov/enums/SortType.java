package org.mitenkov.enums;

import lombok.Getter;

@Getter
public enum SortType {
    TITLE("title"),
    PRIORITY("priority"),
    DEADLINE("deadline");

    private final String column;

    SortType(String column) {
        this.column = column;
    }

}
