package me.theseems.noteservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Value
public class NoteUpdateInDto extends NoteBaseDto {
    @NotNull
    LocalDateTime createdAt;

    @JsonCreator
    public NoteUpdateInDto(@JsonProperty("content") String content,
                           @JsonProperty("title") String title,
                           @JsonProperty("createdAt") LocalDateTime createdAt) {
        super(content, title);
        this.createdAt = createdAt;
    }
}
