package me.theseems.noteservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@NonFinal
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NoteBaseDto {
    @Size(min = 1, max = 10_000)
    @NotBlank
    String content;

    @Size(min = 1, max = 255)
    @NotBlank
    String title;
}
