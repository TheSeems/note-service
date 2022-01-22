package me.theseems.noteservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class NoteInDto extends NoteBaseDto {
    @JsonCreator
    public NoteInDto(@JsonProperty("content") String content, @JsonProperty("title") String title) {
        super(content, title);
    }
}
