package org.mitenkov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mitenkov.controller.converter.CommentDtoConverter;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.dto.CommentDto;
import org.mitenkov.dto.ErrorMessageDto;
import org.mitenkov.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comments")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Everything worked correct",
                content = {@Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentDto.class)
                )}),
        @ApiResponse(responseCode = "400", description = "Invalid data supplied, bad request",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
        @ApiResponse(responseCode = "404", description = "Object not found",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
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
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@RequestBody CommentAddRequest request) {
        commentService.add(request);
    }

}
