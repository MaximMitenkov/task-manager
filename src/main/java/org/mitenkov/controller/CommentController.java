package org.mitenkov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mitenkov.controller.converter.CommentDtoConverter;
import org.mitenkov.controller.converter.OutboxMessageDtoConverter;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.dto.CommentDto;
import org.mitenkov.dto.ErrorMessageDto;
import org.mitenkov.entity.OutboxMessage;
import org.mitenkov.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comments")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = "Invalid data supplied, bad request",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
        @ApiResponse(responseCode = "404", description = "Object not found",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
})
@Slf4j
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentDtoConverter commentDtoConverter;
    private final OutboxMessageDtoConverter outboxMessageDtoConverter;
    private final KafkaTemplate<String, CommentDto> kafkaTemplate;

    @Value("${kafka.topics.comment-topic}")
    private final String topic;

    @GetMapping
    @Operation(summary = "get comments", description = "Get comments by author nickname.")
    public Page<CommentDto> getCommentsByNickname(@RequestParam(value = "nick") String nickname,
                                                  Pageable pageable) {
        return commentService.findAllByNickname(nickname, pageable).map(commentDtoConverter::toDto);
    }

    @PostMapping
    @Operation(summary = "add comment")
    public CommentDto createComment(@RequestBody CommentAddRequest request) {
        return commentDtoConverter.toDto(commentService.add(request));
    }

    @SneakyThrows
    @Scheduled(fixedDelay = 1000)
    public void sendMessages() {
        List<OutboxMessage> messages = commentService.findMessages();
        if (messages.isEmpty()) {
            return;
        }
        messages.stream()
                .map(outboxMessageDtoConverter::toDto)
                .forEach(msg -> kafkaTemplate.send(msg.topic(), msg.payload()));
        kafkaTemplate.flush();
        commentService.deleteById(messages.stream()
                .map(OutboxMessage::getId)
                .collect(Collectors.toSet()));
    }

}
