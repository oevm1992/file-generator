package com.rindus.generator.service;

import com.rindus.generator.file.FileExtension;
import com.rindus.generator.model.CommentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Value("${jsonplaceholder.base.url}")
    private String jsonPlaceBaseUrl;

    @Value("${comments.path}")
    private String commentsPath;

    @Autowired
    private PetitionService petition;

    @Autowired
    FileGeneratorService fileGeneratorService;

    public byte[] getCommentFile(FileExtension extension, Long id) throws Exception {
        CommentModel resourceResponse = (CommentModel) petition.get(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id), CommentModel.class);
        return fileGeneratorService.createFile(resourceResponse, extension);
    }

    public byte[] createCommentFile(CommentModel comment, FileExtension extension) throws Exception {
        CommentModel resourceResponse = (CommentModel) petition.post(jsonPlaceBaseUrl.concat(commentsPath), comment, CommentModel.class);
        return fileGeneratorService.createFile(resourceResponse, extension);
    }

    public byte[] updateCommentFile(CommentModel comment, Long id, FileExtension extension) throws Exception {
        HttpEntity<CommentModel> entity = new HttpEntity<CommentModel>(comment);
        CommentModel resourceResponse = (CommentModel) petition.exchange(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id), comment, CommentModel.class, entity, HttpMethod.PUT);
        return fileGeneratorService.createFile(resourceResponse, extension);
    }

    public byte[] patchCommentFile(CommentModel comment, Long id, FileExtension extension) throws Exception {
        HttpEntity<CommentModel> entity = new HttpEntity<CommentModel>(comment);
        CommentModel resourceResponse = (CommentModel) petition.exchange(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id), comment, CommentModel.class, entity, HttpMethod.PATCH);
        return fileGeneratorService.createFile(resourceResponse, extension);
    }

    public void deleteComment(Long id) throws Exception {
        petition.delete(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id));
    }
}
