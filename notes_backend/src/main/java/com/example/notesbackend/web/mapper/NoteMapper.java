package com.example.notesbackend.web.mapper;

import com.example.notesbackend.model.Note;
import com.example.notesbackend.web.dto.NoteRequest;
import com.example.notesbackend.web.dto.NoteResponse;

// PUBLIC_INTERFACE
public final class NoteMapper {
    /** Map between Note entity and DTOs. */
    private NoteMapper() {}

    // PUBLIC_INTERFACE
    public static Note toEntity(NoteRequest request) {
        /** Convert request to entity (no id). */
        Note n = new Note();
        n.setTitle(request.getTitle());
        n.setContent(request.getContent());
        n.setTagsList(request.getTags());
        return n;
    }

    // PUBLIC_INTERFACE
    public static void updateEntity(Note entity, NoteRequest request) {
        /** Update an existing entity with request fields. */
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setTagsList(request.getTags());
    }

    // PUBLIC_INTERFACE
    public static NoteResponse toResponse(Note entity) {
        /** Convert entity to response DTO. */
        return new NoteResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getTagsList(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
