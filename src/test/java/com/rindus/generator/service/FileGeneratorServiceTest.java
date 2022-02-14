package com.rindus.generator.service;

import com.rindus.generator.file.FileExtension;
import com.rindus.generator.model.PostModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileGeneratorServiceTest {
    @InjectMocks
    FileGeneratorService fileGeneratorService;

    private PostModel postModel;
    private FileExtension fileExtensionJson;
    private FileExtension fileExtensionXml;
    private byte[] file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        postModel = new PostModel(1l, "title", "body", 1l);
        fileExtensionJson = FileExtension.JSON;
        fileExtensionXml = FileExtension.XML;
        file = new byte[5];
    }

    @Test
    void test_create_file_xml() throws Exception {
        assertDoesNotThrow(() -> fileGeneratorService.createFile(postModel, fileExtensionXml));
    }

    @Test
    void test_create_file_json() throws Exception {
        assertThrows(Exception.class, () -> fileGeneratorService.createFile(postModel, FileExtension.valueOf("png")));
    }
}
