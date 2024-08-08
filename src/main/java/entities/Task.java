package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class Task {
    private String title;
    private Priority priority;
    private LocalDate deadline;

    public void printConsole() {
        System.out.println(this.toString());
    };
}
