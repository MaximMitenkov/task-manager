package org.mitenkov;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.helper.DBCleaner;
import org.mitenkov.helper.EntityGenerator;
import org.mitenkov.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class CommentControllerTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CommentService commentService;

    @Autowired
    DBCleaner cleaner;

    @Autowired
    EntityGenerator entityGenerator;

    @BeforeEach
    public void beforeEach() {
        cleaner.cleanAll();
        entityGenerator.generateTasksAndSave();
    }

    @Test
    void addComment() {

    }

}
