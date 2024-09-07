package org.mitenkov;

import org.junit.jupiter.api.AfterEach;
import org.mitenkov.helper.TaskGenerator;
import org.mitenkov.repository.CommentRepository;
import org.mitenkov.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class CommentServiceTest extends BaseTest {
    @Autowired
    CommentService commentService;

    @SpyBean
    CommentRepository commentRepository;

    @Autowired
    TaskGenerator taskGenerator;

    @AfterEach
    void reset() {
        commentRepository.deleteAll();
    }
}
