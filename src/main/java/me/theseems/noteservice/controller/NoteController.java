package me.theseems.noteservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.theseems.noteservice.dto.NoteInDto;
import me.theseems.noteservice.dto.NoteOutDto;
import me.theseems.noteservice.dto.NoteUpdateInDto;
import me.theseems.noteservice.mapper.NoteMapper;
import me.theseems.noteservice.service.NoteService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Operation(summary = "Create a new note")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Note has been found, user has been provided with a note summary"),
        @ApiResponse(
            responseCode = "400",
            description = "Constraint validation failure or malformed request",
            content = @Content(schema = @Schema(description = "Bad request")))})
    @PostMapping("note")
    @ResponseBody
    public NoteOutDto createNote(@RequestBody @Valid NoteInDto noteInDto) {
        return NoteMapper.INSTANCE.map(noteService.createNote(noteInDto));
    }

    @Operation(summary = "Read note")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Note has been found, user has been provided with a note summary"),
        @ApiResponse(
            responseCode = "404",
            description = "Note requested by user is not found",
            content = @Content(schema = @Schema(description = "Not found")))})
    @GetMapping("note/{id}")
    @ResponseBody
    public NoteOutDto getNote(@PathVariable long id) {
        return NoteMapper.INSTANCE.map(noteService.findById(id));
    }

    @Operation(summary = "Update note")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Note has been updated, user has been provided with a note summary"),
        @ApiResponse(
            responseCode = "400",
            description = "Constraint validation failure or malformed request",
            content = @Content(schema = @Schema(description = "Bad request"))),
        @ApiResponse(
            responseCode = "404",
            description = "Note requested by user is not found",
            content = @Content(schema = @Schema(description = "Not found")))})
    @PutMapping("note/{id}")
    @ResponseBody
    public NoteOutDto updateNote(@PathVariable long id, @RequestBody @Valid NoteUpdateInDto updateInDto) {
        return NoteMapper.INSTANCE.map(noteService.updateNote(id, updateInDto));
    }

    @Operation(summary = "Delete note")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Note has been deleted"),
        @ApiResponse(
            responseCode = "404",
            description = "Note requested by user is not found",
            content = @Content(schema = @Schema(description = "Not found")))})
    @DeleteMapping("note/{id}")
    public void deleteNote(@PathVariable long id) {
        noteService.deleteNote(id);
    }
}
