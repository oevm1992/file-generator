package com.rindus.generator.controller;

import com.google.gson.Gson;
import com.rindus.generator.file.FileExtension;
import com.rindus.generator.model.CommentModel;
import com.rindus.generator.service.CommentService;
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

@WebMvcTest(CommentsController.class)
public class CommentsControllerTest {
    @MockBean
    private CommentService commentService;
    private CommentsController commentsController;

    @Autowired
    private MockMvc mockMvc;

    private byte[] response;
    private CommentModel commentModel;
    private CommentModel commentModelCreate;
    private String extension;
    private String invalidExtension;
    private String invalidExtensionResponse;
    private String thirdApiFailResponse;
    private FileExtension fileExtension;
    private Long id;
    private Gson gson;

    @BeforeEach
    void setUp() {
        commentsController = new CommentsController(commentService);
        extension = "json";
        invalidExtension = "png";
        fileExtension = FileExtension.JSON;
        id = 1l;
        response = new byte[5];
        gson = new Gson();
        commentModel = new CommentModel(1l, "name1", "email1", "body1");
        commentModelCreate = new CommentModel(null, "name1", "email1", "body1");
        invalidExtensionResponse = "Could not create File. File Extension is not valid";
        thirdApiFailResponse = "Could not create File because: null";
    }

    @Test
    void test_endpoint_get_comment_correct() throws Exception {

        when(commentService.getCommentFile(fileExtension, id)).thenReturn(response);

        ResponseEntity ctrResponse = commentsController.getComment(id, extension);
        assertEquals(response, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/comments/" + id)
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)).andExpect(status().isOk());
    }

    @Test
    void test_endpoint_get_comment_invalid_extension() throws Exception {

        ResponseEntity ctrResponse = commentsController.getComment(id, invalidExtension);
        assertEquals(invalidExtensionResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/comments/" + id)
                .param("extension", invalidExtension)
                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)).andExpect(status().isBadRequest());
    }

    @Test
    void test_endpoint_get_comment_third_api_fails() throws Exception {

        when(commentService.getCommentFile(fileExtension, id)).thenThrow(RestClientException.class);

        ResponseEntity ctrResponse = commentsController.getComment(id, extension);
        assertEquals(thirdApiFailResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/comments/" + id)
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)).andExpect(status().isInternalServerError());
    }

    @Test
    void test_endpoint_create_comment_correct() throws Exception {

        when(commentService.createCommentFile(commentModelCreate, fileExtension)).thenReturn(response);

        ResponseEntity ctrResponse = commentsController.createComment(commentModelCreate, extension);
        assertEquals(response, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                .content(gson.toJson(commentModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void test_endpoint_create_comment_invalid_extension() throws Exception {

        ResponseEntity ctrResponse = commentsController.createComment(commentModelCreate, invalidExtension);
        assertEquals(invalidExtensionResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                .content(gson.toJson(commentModelCreate))
                .param("extension", invalidExtension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void test_endpoint_create_comment_third_api_fails() throws Exception {

        when(commentService.createCommentFile(any(CommentModel.class), eq(fileExtension))).thenThrow(RestClientException.class);

        ResponseEntity ctrResponse = commentsController.createComment(commentModelCreate, extension);
        assertEquals(thirdApiFailResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                .content(gson.toJson(commentModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
    }

    @Test
    void test_endpoint_update_comment_correct() throws Exception {

        when(commentService.updateCommentFile(commentModelCreate, 1l, fileExtension)).thenReturn(response);

        ResponseEntity ctrResponse = commentsController.updateComment(id, commentModelCreate, extension);
        assertEquals(response, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.put("/comments/" + id)
                .content(gson.toJson(commentModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void test_endpoint_update_comment_invalid_extension() throws Exception {

        ResponseEntity ctrResponse = commentsController.updateComment(id, commentModelCreate, invalidExtension);
        assertEquals(invalidExtensionResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.put("/comments/" + id)
                .content(gson.toJson(commentModelCreate))
                .param("extension", invalidExtension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void test_endpoint_update_comment_third_api_fail() throws Exception {

        when(commentService.updateCommentFile(any(CommentModel.class), eq(1l), eq(fileExtension))).thenThrow(RestClientException.class);

        ResponseEntity ctrResponse = commentsController.updateComment(id, commentModelCreate, extension);
        assertEquals(thirdApiFailResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.put("/comments/" + id)
                .content(gson.toJson(commentModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
    }

    @Test
    void test_endpoint_delete_comment_correct() throws Exception {

        ResponseEntity ctrResponse = commentsController.deleteComment(id);
        assertEquals("Comment with id: 1 deleted correctly", ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/" + id)).andExpect(status().isOk());
    }

    @Test
    void test_endpoint_patch_comment_correct() throws Exception {

        when(commentService.patchCommentFile(commentModelCreate, 1l, fileExtension)).thenReturn(response);

        ResponseEntity ctrResponse = commentsController.patchComment(id, commentModelCreate, extension);
        assertEquals(response, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.patch("/comments/" + id)
                .content(gson.toJson(commentModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void test_endpoint_patch_comment_invalid_extension() throws Exception {

        ResponseEntity ctrResponse = commentsController.patchComment(id, commentModelCreate, invalidExtension);
        assertEquals(invalidExtensionResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.patch("/comments/" + id)
                .content(gson.toJson(commentModelCreate))
                .param("extension", invalidExtension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void test_endpoint_patch_comment_third_api_fail() throws Exception {

        when(commentService.patchCommentFile(any(CommentModel.class), eq(1l), eq(fileExtension))).thenThrow(RestClientException.class);

        ResponseEntity ctrResponse = commentsController.patchComment(id, commentModelCreate, extension);
        assertEquals(thirdApiFailResponse, ctrResponse.getBody());

        mockMvc.perform(MockMvcRequestBuilders.patch("/comments/" + id)
                .content(gson.toJson(commentModelCreate))
                .param("extension", extension)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
    }
}
