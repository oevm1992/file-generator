package com.rindus.generator.service;

import com.rindus.generator.file.FileExtension;
import com.rindus.generator.model.CommentModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    private PetitionService petition;

    @Mock
    FileGeneratorService fileGeneratorService;

    private String jsonPlaceBaseUrl;
    private String commentsPath;
    private CommentModel commentModel;
    private Long id;
    private FileExtension fileExtension;
    private byte[] file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        commentModel = new CommentModel(1l, "name", "email", "body");
        id = 1l;
        fileExtension = FileExtension.JSON;
        file = new byte[5];
        jsonPlaceBaseUrl = "https://jsonplaceholder.typicode.com";
        commentsPath = "/comments";
    }

    @Test
    void test_get_comment_file_correct() throws Exception {

        ReflectionTestUtils.setField(commentService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(commentService, "commentsPath", commentsPath);

        when(petition.get(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id), CommentModel.class)).thenReturn(commentModel);
        when(fileGeneratorService.createFile(commentModel, fileExtension)).thenReturn(file);

        assertEquals(file, commentService.getCommentFile(fileExtension, id));
    }

    @Test
    void test_get_comment_file_fail() throws Exception {

        ReflectionTestUtils.setField(commentService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(commentService, "commentsPath", commentsPath);

        when(petition.get(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id), CommentModel.class)).thenReturn(commentModel);
        when(fileGeneratorService.createFile(commentModel, fileExtension)).thenThrow(Exception.class);

        assertThrows(Exception.class, () -> commentService.getCommentFile(fileExtension, id));
    }

    @Test
    void test_create_comment_file_correct() throws Exception {

        ReflectionTestUtils.setField(commentService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(commentService, "commentsPath", commentsPath);

        when(petition.post(jsonPlaceBaseUrl.concat(commentsPath), commentModel, CommentModel.class)).thenReturn(commentModel);
        when(fileGeneratorService.createFile(commentModel, fileExtension)).thenReturn(file);

        assertEquals(file, commentService.createCommentFile(commentModel, fileExtension));
    }

    @Test
    void test_create_comment_file_fail() throws Exception {

        ReflectionTestUtils.setField(commentService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(commentService, "commentsPath", commentsPath);

        when(petition.post(jsonPlaceBaseUrl.concat(commentsPath), commentModel, CommentModel.class)).thenReturn(commentModel);
        when(fileGeneratorService.createFile(commentModel, fileExtension)).thenThrow(Exception.class);

        assertThrows(Exception.class, () -> commentService.createCommentFile(commentModel, fileExtension));
    }

    @Test
    void test_update_comment_file_correct() throws Exception {

        ReflectionTestUtils.setField(commentService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(commentService, "commentsPath", commentsPath);

        when(petition.exchange(eq(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id)), eq(commentModel),
                eq(CommentModel.class), any(HttpEntity.class), eq(HttpMethod.PUT))).thenReturn(commentModel);

        when(fileGeneratorService.createFile(commentModel, fileExtension)).thenReturn(file);

        assertEquals(file, commentService.updateCommentFile(commentModel, id, fileExtension));
    }

    @Test
    void test_update_comment_file_fail() throws Exception {

        ReflectionTestUtils.setField(commentService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(commentService, "commentsPath", commentsPath);

        when(petition.exchange(eq(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id)), eq(commentModel),
                eq(CommentModel.class), any(HttpEntity.class), eq(HttpMethod.PUT))).thenReturn(commentModel);

        when(fileGeneratorService.createFile(commentModel, fileExtension)).thenThrow(Exception.class);

        assertThrows(Exception.class, () -> commentService.updateCommentFile(commentModel, id, fileExtension));
    }

    @Test
    void test_patch_comment_file_correct() throws Exception {

        ReflectionTestUtils.setField(commentService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(commentService, "commentsPath", commentsPath);

        when(petition.exchange(eq(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id)), eq(commentModel),
                eq(CommentModel.class), any(HttpEntity.class), eq(HttpMethod.PATCH))).thenReturn(commentModel);

        when(fileGeneratorService.createFile(commentModel, fileExtension)).thenReturn(file);

        assertEquals(file, commentService.patchCommentFile(commentModel, id, fileExtension));
    }

    @Test
    void test_patch_comment_file_fail() throws Exception {

        ReflectionTestUtils.setField(commentService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(commentService, "commentsPath", commentsPath);

        when(petition.exchange(eq(jsonPlaceBaseUrl.concat(commentsPath).concat("/" + id)), eq(commentModel),
                eq(CommentModel.class), any(HttpEntity.class), eq(HttpMethod.PATCH))).thenReturn(commentModel);

        when(fileGeneratorService.createFile(commentModel, fileExtension)).thenThrow(Exception.class);

        assertThrows(Exception.class, () -> commentService.patchCommentFile(commentModel, id, fileExtension));

    }

    @Test
    void test_delete_comment_file_correct() throws Exception {

        ReflectionTestUtils.setField(commentService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(commentService, "commentsPath", commentsPath);

        assertDoesNotThrow(() -> commentService.deleteComment(id));
    }

}
