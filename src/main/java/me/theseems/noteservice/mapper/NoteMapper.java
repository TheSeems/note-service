package me.theseems.noteservice.mapper;

import me.theseems.noteservice.dto.NoteInDto;
import me.theseems.noteservice.dto.NoteOutDto;
import me.theseems.noteservice.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(target = "noteId", source = "id")
    NoteOutDto map(Note note);

    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Note map(NoteInDto noteInDto);
}
