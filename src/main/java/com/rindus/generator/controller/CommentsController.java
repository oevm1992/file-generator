package com.rindus.generator.controller;

import com.rindus.generator.exception.FileGeneratorException;
import com.rindus.generator.file.FileExtension;
import com.rindus.generator.model.CommentModel;
import com.rindus.generator.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/comments")
public class CommentsController {

    private CommentService commentService;

    @Autowired
    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity getComment(@PathVariable Long id, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=all_posts." + extension);
        try {
            return new ResponseEntity<>(commentService.getCommentFile(FileExtension.valueOf(extension.toUpperCase()), id), responseHeaders, HttpStatus.OK);
        } catch (IllegalArgumentException | FileGeneratorException e) {
            return new ResponseEntity<>("Could not create File. File Extension is not valid", responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity createComment(@RequestBody CommentModel comment, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=create_post." + extension);
        try {
            return new ResponseEntity<>(commentService.createCommentFile(comment, FileExtension.valueOf(extension.toUpperCase())), responseHeaders, HttpStatus.OK);
        } catch (IllegalArgumentException | FileGeneratorException e) {
            return new ResponseEntity<>("Could not create File. File Extension is not valid", responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody CommentModel comment, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=create_post." + extension);
        try {
            return new ResponseEntity<>(commentService.updateCommentFile(comment, id, FileExtension.valueOf(extension.toUpperCase())), responseHeaders, HttpStatus.OK);
        } catch (IllegalArgumentException | FileGeneratorException e) {
            return new ResponseEntity<>("Could not create File. File Extension is not valid", responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>(String.format("Comment with id: %s deleted correctly", id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Could not delete Comment with id %s because %s ", id, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity patchComment(@PathVariable Long id, @RequestBody CommentModel comment, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=create_post." + extension);
        try {
            return new ResponseEntity<>(commentService.patchCommentFile(comment, id, FileExtension.valueOf(extension.toUpperCase())), responseHeaders, HttpStatus.OK);
        } catch (IllegalArgumentException | FileGeneratorException e) {
            return new ResponseEntity<>("Could not create File. File Extension is not valid", responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
