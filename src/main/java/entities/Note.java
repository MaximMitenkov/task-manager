package entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public abstract class Note {
    private int id;
    private String title;
    private int priority;
    private LocalDate deadline;

    public void printConsole() {
        System.out.println(this.toString());
    };
}
