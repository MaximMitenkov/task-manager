package org.mitenkov.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    private String author;

    private Timestamp dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    @ToString.Exclude
    private Task task;
}
