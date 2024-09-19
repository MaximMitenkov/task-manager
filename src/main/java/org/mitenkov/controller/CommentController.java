package org.mitenkov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Comments")
@Slf4j
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentDtoConverter commentDtoConverter;

    @GetMapping
    @Operation(summary = "get comments", description = "Get comments by author nickname.")
    public List<CommentDto> getCommentsByNickname(@RequestParam(value = "nick") String nickname) {
        return commentService.findAllByNickname(nickname).stream()
                .map(commentDtoConverter::toDto)
                .toList();
    }

    @PostMapping
    @Operation(summary = "add comment")
    public CommentDto createComment(@RequestBody CommentAddRequest request) {
        return commentDtoConverter.toDto(commentService.add(request));
    }

}
