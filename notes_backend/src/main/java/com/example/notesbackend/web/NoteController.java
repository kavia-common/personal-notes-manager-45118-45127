package com.example.notesbackend.web;

import com.example.notesbackend.model.Note;
import com.example.notesbackend.service.NoteService;
import com.example.notesbackend.web.dto.NoteRequest;
import com.example.notesbackend.web.dto.NoteResponse;
import com.example.notesbackend.web.mapper.NoteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/notes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Notes", description = "CRUD operations for notes with pagination, sorting, and search")
public class NoteController {

    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    // PUBLIC_INTERFACE
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new note",
            description = "Creates a note with title, optional content, and optional tags."
    )
    public NoteResponse create(@Valid @RequestBody NoteRequest request) {
        /** Create a new note. */
        Note created = service.create(request);
        return NoteMapper.toResponse(created);
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(
            summary = "List notes",
            description = "Returns a paginated list of notes. Supports search via 'q' and sorting by title, createdAt, updatedAt."
    )
    public Page<NoteResponse> list(
            @Parameter(description = "Search query applied to title and content") @RequestParam(value = "q", required = false) String q,
            @Parameter(description = "Page number (0-based)") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(value = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort, e.g., 'createdAt,desc' or 'title,asc'") @RequestParam(value = "sort", required = false) String sortParam
    ) {
        /** List notes with pagination, sorting, search. */
        Sort sort = parseSort(sortParam);
        Page<Note> result = service.list(q, page, size, sort);
        return service.mapPage(result, NoteMapper::toResponse);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(summary = "Get note by id", description = "Returns a note by its id.")
    public NoteResponse get(@PathVariable("id") Long id) {
        /** Get a note by id. */
        return NoteMapper.toResponse(service.getById(id));
    }

    // PUBLIC_INTERFACE
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update note", description = "Updates title/content/tags for the note with the given id.")
    public NoteResponse update(@PathVariable("id") Long id, @Valid @RequestBody NoteRequest request) {
        /** Update a note. */
        return NoteMapper.toResponse(service.update(id, request));
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete note", description = "Deletes a note by id.")
    public void delete(@PathVariable("id") Long id) {
        /** Delete a note by id. */
        service.delete(id);
    }

    private Sort parseSort(String sortParam) {
        if (sortParam == null || sortParam.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }
        // Support comma separated property,direction
        String[] parts = sortParam.split(",");
        String property = parts[0].trim();
        Sort.Direction direction = Sort.Direction.DESC;
        if (parts.length > 1) {
            try {
                direction = Sort.Direction.fromString(parts[1].trim());
            } catch (IllegalArgumentException ignored) {
                direction = Sort.Direction.DESC;
            }
        }
        return Sort.by(direction, property);
    }
}
