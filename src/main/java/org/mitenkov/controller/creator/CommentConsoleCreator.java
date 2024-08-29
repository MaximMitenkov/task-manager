package org.mitenkov.controller.creator;

import lombok.RequiredArgsConstructor;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.Task;

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
                .author(nickname).build();
    }

}
