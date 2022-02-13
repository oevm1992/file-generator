package com.rindus.generator.controller;

import com.rindus.generator.enums.Extension;
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

    private static final String JSON = "json";
    private static final String XML = "xml";

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity getPosts(@PathVariable Long id, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=all_posts." + extension);
        try {
            if (Extension.JSON.getId().equals(extension) || Extension.XML.getId().equals(extension))
                return new ResponseEntity<>(postService.getPostFile(extension, id), responseHeaders, HttpStatus.OK);
            else
                return new ResponseEntity<>("Could not create File param should be xml or json", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity createPost(@RequestBody PostModel post, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=create_post." + extension);
        try {
            if (Extension.JSON.getId().equals(extension) || Extension.XML.getId().equals(extension))
                return new ResponseEntity<>(postService.createPostFile(post, extension), responseHeaders, HttpStatus.OK);
            else
                return new ResponseEntity<>("Could not create File param should be xml or json", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity updatePost(@PathVariable Long id, @RequestBody PostModel post, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=create_post." + extension);
        try {
            if (Extension.JSON.getId().equals(extension) || Extension.XML.getId().equals(extension))
                return new ResponseEntity<>(postService.updatePostFile(post, id, extension), responseHeaders, HttpStatus.OK);
            else
                return new ResponseEntity<>("Could not create File param should be xml or json", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(String.format("Post with id: %s deleted correctly", id), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Could not delete Post with id %s because %s ", id, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity patchPost(@PathVariable Long id, @RequestBody PostModel post, @RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=create_post." + extension);
        try {
            if (Extension.JSON.getId().equals(extension) || Extension.XML.getId().equals(extension))
                return new ResponseEntity<>(postService.patchPostFile(post, id, extension), responseHeaders, HttpStatus.OK);
            else
                return new ResponseEntity<>("Could not create File param should be xml or json", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create File because: " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
