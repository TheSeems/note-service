package me.theseems.noteservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.theseems.noteservice.dto.NoteInDto;
import me.theseems.noteservice.dto.NoteUpdateInDto;
import me.theseems.noteservice.service.NoteService;
import me.theseems.noteservice.test.NoteTest;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class NoteControllerTest extends NoteTest {
    private Long createdId;

    @Autowired
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        createdId = noteService.createNote(new NoteInDto("hello, world", "first note")).getId();
    }

    @Test
    public void findNote_Success() throws Exception {
        mockMvc.perform(get("/note/{id}", createdId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.title", is("first note")))
            .andExpect(jsonPath("$.body.content", is("hello, world")))
            .andExpect(jsonPath("$.body.createdAt", notNullValue()));
    }

    @Test
    public void updateNote_Success() throws Exception {
        LocalDateTime current = LocalDateTime.now();
        mockMvc.perform(
                put("/note/{id}", createdId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(export(new NoteUpdateInDto("world, hello", "note first", current))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.title", is("note first")))
            .andExpect(jsonPath("$.body.content", is("world, hello")))
            .andExpect(jsonPath("$.body.createdAt", isSameLocalDateTime(current)));
    }

    @Test
    public void deleteNote_Success() throws Exception {
        mockMvc.perform(delete("/note/{id}", createdId));
        mockMvc.perform(get("/note/{id}", createdId))
            .andExpect(jsonPath("$.success", is(false)))
            .andExpect(jsonPath("$.message", is("Not found")));
    }

    private AssertionMatcher<String> isSameLocalDateTime(LocalDateTime value) {
        return new AssertionMatcher<>() {
            @Override
            public void assertion(String actual) throws AssertionError {
                Assertions.assertEquals(value, LocalDateTime.parse(actual));
            }
        };
    }

    private String export(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
