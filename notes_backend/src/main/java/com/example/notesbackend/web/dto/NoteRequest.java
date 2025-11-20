package com.example.notesbackend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

// PUBLIC_INTERFACE
public class NoteRequest {
    /** Request body for creating/updating a note. */
    @Schema(description = "Title of the note", example = "Grocery List", maxLength = 200, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 200)
    private String title;

    @Schema(description = "Content/body of the note", example = "Buy milk, eggs, and bread", maxLength = 10000)
    @Size(max = 10000)
    private String content;

    @Schema(description = "List of tags", example = "[\"personal\",\"todo\"]")
    private List<String> tags;

    public NoteRequest() {}

    public NoteRequest(String title, String content, List<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    // PUBLIC_INTERFACE
    public void setTitle(String title) {
        /** Set title for note creation or update. */
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    // PUBLIC_INTERFACE
    public void setContent(String content) {
        /** Set content for note creation or update. */
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    // PUBLIC_INTERFACE
    public void setTags(List<String> tags) {
        /** Set tags for note creation or update. */
        this.tags = tags;
    }
}
