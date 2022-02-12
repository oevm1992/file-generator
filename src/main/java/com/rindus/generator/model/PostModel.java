package com.rindus.generator.model;

import lombok.Data;

@Data
public class PostModel {

    private Long id;
    private String title;
    private String body;
    private Long userId;


    public PostModel(Long id, String title, String body, Long userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }


}
