package com.rindus.generator.model;

import lombok.Data;

import java.util.List;

@Data
public class PostModelList {
    private List<PostModel> posts;

    public PostModelList(List<PostModel> postModelList) {
        this.posts = postModelList;
    }
}
