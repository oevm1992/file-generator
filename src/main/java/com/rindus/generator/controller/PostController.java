package com.rindus.generator.controller;

import com.rindus.generator.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity getPosts(@RequestParam String extension) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + "all_posts." + extension);
        try {
            if(extension.equals("json")) return new ResponseEntity<>(postService.getAllPostsAsJson(), responseHeaders, HttpStatus.OK);
            else if(extension.equals("xml")) return new ResponseEntity<>(postService.getAllPostsAsXml(), responseHeaders, HttpStatus.OK);
            else return new ResponseEntity<>("Could not create File param should be xml or json", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return new ResponseEntity<>("Could not create File " + e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
