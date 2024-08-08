package entities;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
public abstract class Task {
    private String title;
    private Priority priority;
    private LocalDate deadline;

    public void printConsole() {
        System.out.println(this.toString());
    };
}
