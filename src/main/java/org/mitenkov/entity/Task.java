package org.mitenkov.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.mitenkov.enums.Priority;
import org.mitenkov.enums.TaskType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bug.class, name = "bug"),
        @JsonSubTypes.Type(value = Feature.class, name = "feature")
})
@Data
@NoArgsConstructor
@SuperBuilder
public abstract class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Priority priority;

    private LocalDate deadline;

    @ToString.Exclude
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public abstract TaskType getTaskType();
}

