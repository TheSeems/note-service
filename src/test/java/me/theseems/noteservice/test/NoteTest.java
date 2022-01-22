package me.theseems.noteservice.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.theseems.noteservice.NoteServiceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest(classes = NoteServiceApplication.class)
@Transactional
public class NoteTest {
    protected MockMvc mockMvc;
    protected ObjectMapper mapper;

    @Autowired
    public void setContext(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Mapper with LocalDateTime support
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }
}
