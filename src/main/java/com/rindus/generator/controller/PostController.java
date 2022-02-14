package com.rindus.generator.controller;

import com.rindus.generator.exception.FileGeneratorException;
import com.rindus.generator.file.FileExtension;
import com.rindus.generator.model.PostModel;
import com.rindus.generator.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity getPosts(@PathVariable Long id, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=all_posts." + extension);
        try {
            return new ResponseEntity<>(postService.getPostFile(FileExtension.valueOf(extension.toUpperCase()), id), responseHeaders, HttpStatus.OK);
        } catch (IllegalArgumentException i) {
            return new ResponseEntity<>("Could not create File. File Extension is not valid", responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity createPost(@RequestBody PostModel post, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=create_post." + extension);
        try {
            return new ResponseEntity<>(postService.createPostFile(post, FileExtension.valueOf(extension.toUpperCase())), responseHeaders, HttpStatus.OK);
        } catch (IllegalArgumentException | FileGeneratorException e) {
            return new ResponseEntity<>("Could not create File. File Extension is not valid", responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity updatePost(@PathVariable Long id, @RequestBody PostModel post, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=create_post." + extension);
        try {
            return new ResponseEntity<>(postService.updatePostFile(post, id, FileExtension.valueOf(extension.toUpperCase())), responseHeaders, HttpStatus.OK);
        } catch (IllegalArgumentException | FileGeneratorException e) {
            return new ResponseEntity<>("Could not create File. File Extension is not valid", responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(String.format("Post with id: %s deleted correctly", id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Could not delete Post with id %s because %s ", id, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity patchPost(@PathVariable Long id, @RequestBody PostModel post, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=create_post." + extension);
        try {
            return new ResponseEntity<>(postService.patchPostFile(post, id, FileExtension.valueOf(extension.toUpperCase())), responseHeaders, HttpStatus.OK);
        } catch (IllegalArgumentException | FileGeneratorException e) {
            return new ResponseEntity<>("Could not create File. File Extension is not valid", responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
