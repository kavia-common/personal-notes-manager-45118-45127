package com.example.notesbackend.service;

import com.example.notesbackend.model.Note;
import com.example.notesbackend.repository.NoteRepository;
import com.example.notesbackend.web.dto.NoteRequest;
import com.example.notesbackend.web.mapper.NoteMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
public class NoteService {

    private static final Set<String> SORT_WHITELIST = Set.of("title", "createdAt", "updatedAt", "id");

    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    // PUBLIC_INTERFACE
    public Note create(NoteRequest request) {
        /** Create a new note from request. */
        Note entity = NoteMapper.toEntity(request);
        return repository.save(entity);
    }

    // PUBLIC_INTERFACE
    public Page<Note> list(String q, int page, int size, Sort sort) {
        /** List notes with optional search query and pageable sorting. */
        Pageable pageable = PageRequest.of(page, size, whitelistSort(sort));
        if (StringUtils.hasText(q)) {
            return repository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(q, q, pageable);
        }
        return repository.findAll(pageable);
    }

    // PUBLIC_INTERFACE
    public Note getById(Long id) {
        /** Get note by id. */
        return repository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
    }

    // PUBLIC_INTERFACE
    public Note update(Long id, NoteRequest request) {
        /** Update note with given id. */
        Note entity = getById(id);
        NoteMapper.updateEntity(entity, request);
        return repository.save(entity);
    }

    // PUBLIC_INTERFACE
    public void delete(Long id) {
        /** Delete note by id. */
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoteNotFoundException(id);
        }
    }

    private Sort whitelistSort(Sort requested) {
        if (requested == null || requested.isUnsorted()) return Sort.by(Sort.Direction.DESC, "createdAt");
        List<Sort.Order> whitelisted = requested.stream()
                .map(order -> {
                    String property = order.getProperty();
                    if (!SORT_WHITELIST.contains(property)) {
                        // default to createdAt if field not allowed
                        return new Sort.Order(order.getDirection(), "createdAt");
                    }
                    return order;
                })
                .toList();
        return Sort.by(whitelisted);
    }

    // PUBLIC_INTERFACE
    public <T> Page<T> mapPage(Page<Note> page, Function<Note, T> mapper) {
        /** Helper to map page content while preserving pageable metadata. */
        List<T> content = page.getContent().stream().map(mapper).toList();
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }
}
