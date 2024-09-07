package org.mitenkov.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mitenkov.controller.converter.CommentDtoConverter;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.dto.CommentDto;
import org.mitenkov.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Slf4j
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentDtoConverter commentDtoConverter;

    @GetMapping
    public List<CommentDto> getCommentsByNickname(@RequestParam(value = "nick") String nickname) {
        return commentService.findAllByNickname(nickname).stream()
                .map(commentDtoConverter::createDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@RequestBody CommentAddRequest request) {
        commentService.add(request);
    }

}
