package org.mitenkov.controller.creator;

import lombok.RequiredArgsConstructor;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.Task;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

@RequiredArgsConstructor
public class CommentConsoleCreator {

    private final Scanner scanner;

    public Comment create(Task task) {
        System.out.println("Write text of the comment:");
        String text = scanner.nextLine();
        System.out.println("Write your nickname:");
        String nickname = scanner.nextLine();
        return Comment.builder()
                .task(task)
                .content(text)
                .dateTime(Timestamp.valueOf(LocalDateTime.now()))
                .author(nickname).build();
    }

}
