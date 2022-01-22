package me.theseems.noteservice;

import me.theseems.noteservice.dto.NoteInDto;
import me.theseems.noteservice.dto.NoteUpdateInDto;
import me.theseems.noteservice.entity.Note;
import me.theseems.noteservice.service.NoteService;
import me.theseems.noteservice.test.NoteTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

public class NoteServiceTest extends NoteTest {
    @Autowired
    private NoteService noteService;

    private Note created;

    @BeforeEach
    void setUp() {
        created = noteService.createNote(new NoteInDto("hello, world", "first note"));
    }

    @Test
    public void createNote_Success() {
        Assertions.assertEquals("hello, world", created.getContent());
        Assertions.assertEquals("first note", created.getTitle());
        Assertions.assertNotNull(created.getCreatedAt());
    }

    @Test
    public void findNote_Success() {
        Note found = noteService.findById(created.getId());
        Assertions.assertEquals("hello, world", found.getContent());
        Assertions.assertEquals("first note", found.getTitle());
        Assertions.assertEquals(created.getCreatedAt(), found.getCreatedAt());
    }

    @Test
    public void updateNote_Success() {
        LocalDateTime current = LocalDateTime.now();
        noteService.updateNote(created.getId(), new NoteUpdateInDto(
            "world, hello",
            "note first",
            current));

        Note found = noteService.findById(created.getId());
        Assertions.assertEquals("world, hello", found.getContent());
        Assertions.assertEquals("note first", found.getTitle());
        Assertions.assertEquals(current, found.getCreatedAt());
    }

    @Test
    public void deleteNote_Success() {
        noteService.deleteNote(created.getId());
        Assertions.assertThrows(EntityNotFoundException.class, () -> noteService.findById(created.getId()));
    }
}
