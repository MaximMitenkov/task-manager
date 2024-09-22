package org.mitenkov.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.mitenkov.enums.TaskType;

import java.util.ArrayList;

@Entity
@DiscriminatorValue("BUG")
@JsonTypeName("bug")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Bug extends Task {
    private String version;

    @Builder.Default
    ArrayList<Comment> comments = new ArrayList<>();

    @Override
    public TaskType getTaskType() {
        return TaskType.BUG;
    }
}
