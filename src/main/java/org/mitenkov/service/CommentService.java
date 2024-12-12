package org.mitenkov.service;

import lombok.AllArgsConstructor;
import org.mitenkov.Authintication.AuthHolder;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.OutboxMessage;
import org.mitenkov.entity.User;
import org.mitenkov.event.CommentEventSender;
import org.mitenkov.repository.CommentRepository;
import org.mitenkov.repository.OutboxMessageRepository;
import org.mitenkov.repository.TaskRepository;
import org.mitenkov.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentEventSender commentEventSender;
    private final CommentRepository commentRepository;
    private final OutboxMessageRepository outboxMessageRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Value("${kafka.topics.comment-topic}")
    private final String topic;

    @Transactional
    public Comment add(CommentAddRequest request) {
        User user = userRepository.getReferenceById(AuthHolder.getCurrentUser().getId());
        Comment comment = commentRepository.save(Comment.builder()
                .dateTime(request.dateTime())
                .content(request.content())
                .task(taskRepository.findById(request.taskId()).orElseThrow())
                .createdBy(user)
                .build());
        commentEventSender.send(topic, comment);
        return comment;
    }

    public Page<Comment> findAllByNickname(String nickname, Pageable pageable) {
        return commentRepository.findByAuthor(nickname, pageable);
    }

    public List<OutboxMessage> findMessages() {
        return outboxMessageRepository.findByTopic(topic);
    }

    public void deleteById(Long id) {
        outboxMessageRepository.deleteById(id);
    }
}
