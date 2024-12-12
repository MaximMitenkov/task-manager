package org.mitenkov.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mitenkov.dto.CommentDto;
import org.mitenkov.dto.OutboxCommentMessageDto;
import org.mitenkov.entity.OutboxMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxMessageDtoConverter {

    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public OutboxCommentMessageDto toDto(OutboxMessage msg) {
        return new OutboxCommentMessageDto(msg.getTopic(), mapper.readValue(msg.getPayload(), CommentDto.class));
    }
}
