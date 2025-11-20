package com.example.notesbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Note JPA entity.
 */
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String title;

    @Size(max = 10000)
    @Column(columnDefinition = "TEXT")
    private String content;

    // Stored as comma separated for simplicity. You can refactor to a join table if needed.
    @Size(max = 1000)
    @Column(length = 1000)
    private String tags; // comma-separated

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public Note() {}

    public Note(String title, String content, List<String> tagsList) {
        this.title = title;
        this.content = content;
        setTagsList(tagsList);
    }

    public Long getId() {
        return id;
    }

    // PUBLIC_INTERFACE
    public void setId(Long id) {
        /** Setter mainly for mapping/testing. */
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    // PUBLIC_INTERFACE
    public void setTitle(String title) {
        /** Set note title. */
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    // PUBLIC_INTERFACE
    public void setContent(String content) {
        /** Set note content. */
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    // PUBLIC_INTERFACE
    public void setTags(String tags) {
        /** Set tags as comma-separated string. */
        this.tags = tags;
    }

    public List<String> getTagsList() {
        if (tags == null || tags.isBlank()) return List.of();
        return Arrays.stream(tags.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }

    // PUBLIC_INTERFACE
    public void setTagsList(List<String> tagsList) {
        /** Set tags from a list as comma-separated string. */
        if (tagsList == null || tagsList.isEmpty()) {
            this.tags = null;
        } else {
            this.tags = String.join(",", tagsList.stream().map(String::trim).toList());
        }
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    // PUBLIC_INTERFACE
    public void setCreatedAt(Instant createdAt) {
        /** Set creation timestamp (used for tests). */
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    // PUBLIC_INTERFACE
    public void setUpdatedAt(Instant updatedAt) {
        /** Set update timestamp (used for tests). */
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note note)) return false;
        return Objects.equals(id, note.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
