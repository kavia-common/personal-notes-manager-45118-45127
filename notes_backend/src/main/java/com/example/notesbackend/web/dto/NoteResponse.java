package com.example.notesbackend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

// PUBLIC_INTERFACE
public class NoteResponse {
    /** Response payload representing a note. */
    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @Schema(description = "Title", example = "Grocery List")
    private String title;

    @Schema(description = "Content/body", example = "Buy milk, eggs, and bread")
    private String content;

    @Schema(description = "Tags", example = "[\"personal\",\"todo\"]")
    private List<String> tags;

    @Schema(description = "Creation timestamp (UTC ISO-8601)")
    private Instant createdAt;

    @Schema(description = "Update timestamp (UTC ISO-8601)")
    private Instant updatedAt;

    public NoteResponse() {}

    public NoteResponse(Long id, String title, String content, List<String> tags, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { /** Set id. */ this.id = id; }

    public String getTitle() { return title; }
    // PUBLIC_INTERFACE
    public void setTitle(String title) { /** Set title. */ this.title = title; }

    public String getContent() { return content; }
    // PUBLIC_INTERFACE
    public void setContent(String content) { /** Set content. */ this.content = content; }

    public List<String> getTags() { return tags; }
    // PUBLIC_INTERFACE
    public void setTags(List<String> tags) { /** Set tags. */ this.tags = tags; }

    public Instant getCreatedAt() { return createdAt; }
    // PUBLIC_INTERFACE
    public void setCreatedAt(Instant createdAt) { /** Set createdAt. */ this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    // PUBLIC_INTERFACE
    public void setUpdatedAt(Instant updatedAt) { /** Set updatedAt. */ this.updatedAt = updatedAt; }
}
