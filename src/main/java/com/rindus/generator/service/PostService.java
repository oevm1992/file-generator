package com.rindus.generator.service;

import com.rindus.generator.file.FileExtension;
import com.rindus.generator.model.PostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {

    @Value("${jsonplaceholder.base.url}")
    private String jsonPlaceBaseUrl;

    @Value("${posts.path}")
    private String postPath;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PetitionService petition;

    @Autowired
    FileGeneratorService fileGeneratorService;

    public byte[] getPostFile(FileExtension extension, Long id) throws Exception {
        PostModel resourceResponse = (PostModel) petition.get(jsonPlaceBaseUrl.concat(postPath).concat("/" + id), PostModel.class);
        return fileGeneratorService.createFile(resourceResponse, extension);
    }

    public byte[] createPostFile(PostModel post, FileExtension extension) throws Exception {
        PostModel resourceResponse = (PostModel) petition.post(jsonPlaceBaseUrl.concat(postPath), post, PostModel.class);
        return fileGeneratorService.createFile(resourceResponse, extension);
    }

    public byte[] updatePostFile(PostModel post, Long id, FileExtension extension) throws Exception {
        HttpEntity<PostModel> entity = new HttpEntity<PostModel>(post);
        PostModel resourceResponse = (PostModel) petition.exchange(jsonPlaceBaseUrl.concat(postPath).concat("/" + id), post, PostModel.class, entity, HttpMethod.PUT);
        return fileGeneratorService.createFile(resourceResponse, extension);
    }

    public byte[] patchPostFile(PostModel post, Long id, FileExtension extension) throws Exception {
        HttpEntity<PostModel> entity = new HttpEntity<PostModel>(post);
        PostModel resourceResponse = (PostModel) petition.exchange(jsonPlaceBaseUrl.concat(postPath).concat("/" + id), post, PostModel.class, entity, HttpMethod.PATCH);
        return fileGeneratorService.createFile(resourceResponse, extension);
    }

    public void deletePost(Long id) throws Exception {
        petition.delete(jsonPlaceBaseUrl.concat(postPath).concat("/" + id));
    }

}
