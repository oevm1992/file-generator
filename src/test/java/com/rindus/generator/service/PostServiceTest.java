package com.rindus.generator.service;

import com.rindus.generator.file.FileExtension;
import com.rindus.generator.model.PostModel;
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

public class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    private PetitionService petition;

    @Mock
    FileGeneratorService fileGeneratorService;

    private String jsonPlaceBaseUrl;
    private String postPath;
    private PostModel postModel;
    private Long id;
    private FileExtension fileExtension;
    private byte[] file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        postModel = new PostModel(1l, "title", "body", 1l);
        id = 1l;
        fileExtension = FileExtension.JSON;
        file = new byte[5];
        jsonPlaceBaseUrl = "https://jsonplaceholder.typicode.com";
        postPath = "/posts";
    }

    @Test
    void test_get_post_file_correct() throws Exception {

        ReflectionTestUtils.setField(postService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(postService, "postPath", postPath);

        when(petition.get(jsonPlaceBaseUrl.concat(postPath).concat("/" + id), PostModel.class)).thenReturn(postModel);
        when(fileGeneratorService.createFile(postModel, fileExtension)).thenReturn(file);

        assertEquals(file, postService.getPostFile(fileExtension, id));
    }

    @Test
    void test_get_post_file_fail() throws Exception {

        ReflectionTestUtils.setField(postService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(postService, "postPath", postPath);

        when(petition.get(jsonPlaceBaseUrl.concat(postPath).concat("/" + id), PostModel.class)).thenReturn(postModel);
        when(fileGeneratorService.createFile(postModel, fileExtension)).thenThrow(Exception.class);

        assertThrows(Exception.class, () -> postService.getPostFile(fileExtension, id));
    }

    @Test
    void test_create_post_file_correct() throws Exception {

        ReflectionTestUtils.setField(postService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(postService, "postPath", postPath);

        when(petition.post(jsonPlaceBaseUrl.concat(postPath), postModel, PostModel.class)).thenReturn(postModel);
        when(fileGeneratorService.createFile(postModel, fileExtension)).thenReturn(file);

        assertEquals(file, postService.createPostFile(postModel, fileExtension));
    }

    @Test
    void test_create_post_file_fail() throws Exception {

        ReflectionTestUtils.setField(postService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(postService, "postPath", postPath);

        when(petition.post(jsonPlaceBaseUrl.concat(postPath), postModel, PostModel.class)).thenReturn(postModel);
        when(fileGeneratorService.createFile(postModel, fileExtension)).thenThrow(Exception.class);

        assertThrows(Exception.class, () -> postService.createPostFile(postModel, fileExtension));
    }

    @Test
    void test_update_post_file_correct() throws Exception {

        ReflectionTestUtils.setField(postService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(postService, "postPath", postPath);

        when(petition.exchange(eq(jsonPlaceBaseUrl.concat(postPath).concat("/" + id)), eq(postModel),
                eq(PostModel.class), any(HttpEntity.class), eq(HttpMethod.PUT))).thenReturn(postModel);

        when(fileGeneratorService.createFile(postModel, fileExtension)).thenReturn(file);

        assertEquals(file, postService.updatePostFile(postModel, id, fileExtension));
    }

    @Test
    void test_update_post_file_fail() throws Exception {

        ReflectionTestUtils.setField(postService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(postService, "postPath", postPath);

        when(petition.exchange(eq(jsonPlaceBaseUrl.concat(postPath).concat("/" + id)), eq(postModel),
                eq(PostModel.class), any(HttpEntity.class), eq(HttpMethod.PUT))).thenReturn(postModel);

        when(fileGeneratorService.createFile(postModel, fileExtension)).thenThrow(Exception.class);

        assertThrows(Exception.class, () -> postService.updatePostFile(postModel, id, fileExtension));
    }

    @Test
    void test_patch_post_file_correct() throws Exception {

        ReflectionTestUtils.setField(postService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(postService, "postPath", postPath);

        when(petition.exchange(eq(jsonPlaceBaseUrl.concat(postPath).concat("/" + id)), eq(postModel),
                eq(PostModel.class), any(HttpEntity.class), eq(HttpMethod.PATCH))).thenReturn(postModel);

        when(fileGeneratorService.createFile(postModel, fileExtension)).thenReturn(file);

        assertEquals(file, postService.patchPostFile(postModel, id, fileExtension));
    }

    @Test
    void test_patch_post_file_fail() throws Exception {

        ReflectionTestUtils.setField(postService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(postService, "postPath", postPath);

        when(petition.exchange(eq(jsonPlaceBaseUrl.concat(postPath).concat("/" + id)), eq(postModel),
                eq(PostModel.class), any(HttpEntity.class), eq(HttpMethod.PATCH))).thenReturn(postModel);

        when(fileGeneratorService.createFile(postModel, fileExtension)).thenThrow(Exception.class);

        assertThrows(Exception.class, () -> postService.patchPostFile(postModel, id, fileExtension));

    }

    @Test
    void test_delete_post_file_correct() throws Exception {

        ReflectionTestUtils.setField(postService, "jsonPlaceBaseUrl", jsonPlaceBaseUrl);
        ReflectionTestUtils.setField(postService, "postPath", postPath);

        assertDoesNotThrow(() -> postService.deletePost(id));
    }
}
