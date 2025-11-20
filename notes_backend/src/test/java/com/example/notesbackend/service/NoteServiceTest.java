package com.example.notesbackend.service;

import com.example.notesbackend.model.Note;
import com.example.notesbackend.web.dto.NoteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NoteServiceTest {

    @Autowired
    private NoteService service;

    @Test
    void createAndFetchNote() {
        NoteRequest request = new NoteRequest("Test Title", "Test Content", java.util.List.of("t1","t2"));
        Note created = service.create(request);
        assertThat(created.getId()).isNotNull();

        Note fetched = service.getById(created.getId());
        assertThat(fetched.getTitle()).isEqualTo("Test Title");
        assertThat(fetched.getTagsList()).containsExactly("t1","t2");
    }
}
