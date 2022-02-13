package com.rindus.generator.service;

import com.rindus.generator.enums.Extension;
import com.rindus.generator.util.FileUtils;
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

    public byte[] getPostFile(String extension, Long id) throws Exception {
        PostModel getResponse = (PostModel) petition.get(jsonPlaceBaseUrl.concat(postPath).concat("/" + id), PostModel.class);
        return Extension.JSON.getId().equals(extension) ? FileUtils.createJsonFileByte(getResponse) : FileUtils.createXmlFileByte(getResponse);
    }

    public byte[] createPostFile(PostModel post, String extension) throws Exception {
        PostModel postResponse = (PostModel) petition.post(jsonPlaceBaseUrl.concat(postPath), post, PostModel.class);
        return Extension.JSON.getId().equals(extension) ? FileUtils.createJsonFileByte(postResponse) : FileUtils.createXmlFileByte(postResponse);
    }

    public byte[] updatePostFile(PostModel post, Long id, String extension) throws Exception {
        HttpEntity<PostModel> entity = new HttpEntity<PostModel>(post);
        PostModel postResponse = (PostModel) petition.exchange(jsonPlaceBaseUrl.concat(postPath).concat("/" + id), post, PostModel.class, entity, HttpMethod.PUT);
        return Extension.JSON.getId().equals(extension) ? FileUtils.createJsonFileByte(postResponse) : FileUtils.createXmlFileByte(postResponse);
    }

    public byte[] patchPostFile(PostModel post, Long id, String extension) throws Exception {
        HttpEntity<PostModel> entity = new HttpEntity<PostModel>(post);
        PostModel postResponse = (PostModel) petition.exchange(jsonPlaceBaseUrl.concat(postPath).concat("/" + id), post, PostModel.class, entity, HttpMethod.PATCH);
        return Extension.JSON.getId().equals(extension) ? FileUtils.createJsonFileByte(postResponse) : FileUtils.createXmlFileByte(postResponse);
    }

    public void deletePost(Long id) throws Exception {
        petition.delete(jsonPlaceBaseUrl.concat(postPath).concat("/" + id));
    }

}
