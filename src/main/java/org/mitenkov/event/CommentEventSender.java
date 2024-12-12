package org.mitenkov.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mitenkov.controller.converter.CommentDtoConverter;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.OutboxMessage;
import org.mitenkov.repository.OutboxMessageRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentEventSender {

    private final OutboxMessageRepository outboxMessageRepository;
    private final CommentDtoConverter converter;
    private final ObjectMapper mapper;

    @SneakyThrows
    public void send(String topic, Comment comment) {
        var dto = converter.toDto(comment);
        String json = mapper.writeValueAsString(dto);
        outboxMessageRepository.save(OutboxMessage.builder()
                .payload(json)
                .topic(topic)
                .build());
    }
}
