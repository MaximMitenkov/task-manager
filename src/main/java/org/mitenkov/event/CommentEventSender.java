package org.mitenkov.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mitenkov.controller.converter.CommentDtoConverter;
import org.mitenkov.controller.converter.OutboxMessageDtoConverter;
import org.mitenkov.dto.CommentDto;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.OutboxMessage;
import org.mitenkov.repository.OutboxMessageRepository;
import org.mitenkov.service.CommentService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentEventSender {

    private final OutboxMessageRepository outboxMessageRepository;
    private final OutboxMessageDtoConverter outboxMessageDtoConverter;
    private final KafkaTemplate<String, CommentDto> kafkaTemplate;
    private final CommentService commentService;
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

    @Transactional
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
