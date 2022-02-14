package com.rindus.generator.controller;

import com.google.gson.Gson;
import com.rindus.generator.file.FileExtension;
import com.rindus.generator.model.PostModel;
import com.rindus.generator.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @MockBean
    private PostService postService;
    private PostController postController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockMvc mockMvcException;

    private byte[] response;
    private PostModel postModel;
    private PostModel postModelCreate;
    private String extension;
    private String invalidExtension;
    private String invalidExtensionResponse;
    private String thirdApiFailResponse;
    private FileExtension fileExtension;
    private Long id;
    private Gson gson;

    @BeforeEach
    void setUp() {
        postController = new PostController(postService);
        extension = "json";
        invalidExtension = "png";
        fileExtension = FileExtension.JSON;
        id = 1l;
        response = new byte[5];
        gson = new Gson();
        postModel = new PostModel(1l, "title1", "body1", 1l);
        postModelCreate = new PostModel(null, "title1", "body1", 1l);
        invalidExtensionResponse = "Could not create File. File Extension is not valid";
        thirdApiFailResponse = "Could not create File because: null";
    }

    @Test
    void test_endpoint_get_posts_correct() throws Exception {

        when(postService.getPostFile(fileExtension, id)).thenReturn(response);

        ResponseEntity ctrResponse = postController.getPosts(id, extension);
        assertEquals(response, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/" + id)
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)).andExpect(status().isOk());
    }

    @Test
    void test_endpoint_get_post_invalid_extension() throws Exception {

        ResponseEntity ctrResponse = postController.getPosts(id, invalidExtension);
        assertEquals(invalidExtensionResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/" + id)
                .param("extension", invalidExtension)
                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)).andExpect(status().isBadRequest());
    }

    @Test
    void test_endpoint_get_posts_third_api_fails() throws Exception {

        when(postService.getPostFile(fileExtension, id)).thenThrow(RestClientException.class);

        ResponseEntity ctrResponse = postController.getPosts(id, extension);
        assertEquals(thirdApiFailResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/" + id)
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)).andExpect(status().isInternalServerError());
    }

    @Test
    void test_endpoint_create_post_correct() throws Exception {

        when(postService.createPostFile(postModelCreate, fileExtension)).thenReturn(response);

        ResponseEntity ctrResponse = postController.createPost(postModelCreate, extension);
        assertEquals(response, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .content(gson.toJson(postModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void test_endpoint_create_post_invalid_extension() throws Exception {

        ResponseEntity ctrResponse = postController.createPost(postModelCreate, invalidExtension);
        assertEquals(invalidExtensionResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .content(gson.toJson(postModelCreate))
                .param("extension", invalidExtension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void test_endpoint_create_post_third_api_fails() throws Exception {

        when(postService.createPostFile(any(PostModel.class), eq(fileExtension))).thenThrow(RestClientException.class);

        ResponseEntity ctrResponse = postController.createPost(postModelCreate, extension);
        assertEquals(thirdApiFailResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .content(gson.toJson(postModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
    }

    @Test
    void test_endpoint_update_post_correct() throws Exception {

        when(postService.updatePostFile(postModelCreate, 1l, fileExtension)).thenReturn(response);

        ResponseEntity ctrResponse = postController.updatePost(id, postModelCreate, extension);
        assertEquals(response, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.put("/posts/" + id)
                .content(gson.toJson(postModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void test_endpoint_update_post_invalid_extension() throws Exception {

        ResponseEntity ctrResponse = postController.updatePost(id, postModelCreate, invalidExtension);
        assertEquals(invalidExtensionResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.put("/posts/" + id)
                .content(gson.toJson(postModelCreate))
                .param("extension", invalidExtension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void test_endpoint_update_post_third_api_fail() throws Exception {

        when(postService.updatePostFile(any(PostModel.class), eq(1l), eq(fileExtension))).thenThrow(RestClientException.class);

        ResponseEntity ctrResponse = postController.updatePost(id, postModelCreate, extension);
        assertEquals(thirdApiFailResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.put("/posts/" + id)
                .content(gson.toJson(postModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
    }

    @Test
    void test_endpoint_delete_post_correct() throws Exception {

        ResponseEntity ctrResponse = postController.deletePost(id);
        assertEquals("Post with id: 1 deleted correctly", ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/" + id)).andExpect(status().isOk());
    }

    @Test
    void test_endpoint_patch_post_correct() throws Exception {

        when(postService.patchPostFile(postModelCreate, 1l, fileExtension)).thenReturn(response);

        ResponseEntity ctrResponse = postController.patchPost(id, postModelCreate, extension);
        assertEquals(response, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/" + id)
                .content(gson.toJson(postModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void test_endpoint_patch_post_invalid_extension() throws Exception {

        ResponseEntity ctrResponse = postController.patchPost(id, postModelCreate, invalidExtension);
        assertEquals(invalidExtensionResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/" + id)
                .content(gson.toJson(postModelCreate))
                .param("extension", invalidExtension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void test_endpoint_patch_post_third_api_fail() throws Exception {

        when(postService.patchPostFile(any(PostModel.class), eq(1l), eq(fileExtension))).thenThrow(RestClientException.class);

        ResponseEntity ctrResponse = postController.patchPost(id, postModelCreate, extension);
        assertEquals(thirdApiFailResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/" + id)
                .content(gson.toJson(postModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
    }


}
