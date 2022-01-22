package me.theseems.noteservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Value
public class NoteOutDto extends NoteBaseDto {
    Long noteId;
    LocalDateTime createdAt;

    @JsonCreator
    public NoteOutDto(@JsonProperty("content") String content,
                      @JsonProperty("title") String title,
                      @JsonProperty("noteId") Long noteId,
                      @JsonProperty("createdAt") LocalDateTime createdAt) {
        super(content, title);
        this.noteId = noteId;
        this.createdAt = createdAt;
    }
}
