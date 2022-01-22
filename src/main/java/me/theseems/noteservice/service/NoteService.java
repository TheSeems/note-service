package me.theseems.noteservice.service;

import me.theseems.noteservice.dto.NoteInDto;
import me.theseems.noteservice.dto.NoteUpdateInDto;
import me.theseems.noteservice.entity.Note;
import me.theseems.noteservice.mapper.NoteMapper;
import me.theseems.noteservice.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Transactional
    public Note createNote(NoteInDto noteInDto) {
        return noteRepository.save(NoteMapper.INSTANCE.map(noteInDto));
    }

    @Transactional(readOnly = true)
    public Note findById(long id) {
        return findNote(id);
    }

    @Transactional
    public Note updateNote(long id, NoteUpdateInDto noteUpdateInDto) {
        Note current = findNote(id);
        current.setTitle(noteUpdateInDto.getTitle());
        current.setContent(noteUpdateInDto.getContent());
        current.setCreatedAt(noteUpdateInDto.getCreatedAt());

        return current;
    }

    @Transactional
    public void deleteNote(long id) {
        noteRepository.delete(findNote(id));
    }

    private Note findNote(long id) {
        return noteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
