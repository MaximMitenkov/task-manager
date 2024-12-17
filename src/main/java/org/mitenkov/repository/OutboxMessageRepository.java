package org.mitenkov.repository;

import jakarta.persistence.LockModeType;
import org.mitenkov.entity.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessage, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    List<OutboxMessage> findByTopic(String topic);

}
