package com.rindus.generator.service;

import com.rindus.generator.enums.Extension;
import com.rindus.generator.model.CommentModel;
import com.rindus.generator.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentService {

    @Value("${jsonplaceholder.base.url}")
    private String jsonPlaceBaseUrl;

    @Value("${comments.path}")
    private String commentsPath;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PetitionService petition;

    public byte[] getCommentFile(String extension, Long id) throws Exception {
        CommentModel getResponse = (CommentModel) petition.get(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id), CommentModel.class);
        return Extension.JSON.getId().equals(extension) ? FileUtils.createJsonFileByte(getResponse) : FileUtils.createXmlFileByte(getResponse);
    }

    public byte[] createCommentFile(CommentModel comment, String extension) throws Exception {
        CommentModel postResponse = (CommentModel) petition.post(jsonPlaceBaseUrl.concat(commentsPath), comment, CommentModel.class);
        return Extension.JSON.getId().equals(extension) ? FileUtils.createJsonFileByte(postResponse) : FileUtils.createXmlFileByte(postResponse);
    }

    public byte[] updateCommentFile(CommentModel comment, Long id, String extension) throws Exception {
        HttpEntity<CommentModel> entity = new HttpEntity<CommentModel>(comment);
        CommentModel postResponse = (CommentModel) petition.exchange(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id), comment, CommentModel.class, entity, HttpMethod.PUT);
        return Extension.JSON.getId().equals(extension) ? FileUtils.createJsonFileByte(postResponse) : FileUtils.createXmlFileByte(postResponse);
    }

    public byte[] patchCommentFile(CommentModel comment, Long id, String extension) throws Exception {
        HttpEntity<CommentModel> entity = new HttpEntity<CommentModel>(comment);
        CommentModel postResponse = (CommentModel) petition.exchange(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id), comment, CommentModel.class, entity, HttpMethod.PATCH);
        return Extension.JSON.getId().equals(extension) ? FileUtils.createJsonFileByte(postResponse) : FileUtils.createXmlFileByte(postResponse);
    }

    public void deleteComment(Long id) throws Exception {
        petition.delete(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id));
    }
}
