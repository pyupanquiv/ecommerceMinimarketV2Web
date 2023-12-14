package edu.cibertec.ecommerce.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class UploadFileServiceTest {

    @Test
    void saveImage() throws IOException {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.isEmpty()).thenReturn(false);
        Mockito.when(file.getOriginalFilename()).thenReturn("test.jpg");
        Mockito.when(file.getBytes()).thenReturn(new byte[0]);

        UploadFileService service = new UploadFileService();
        String result = service.saveImage(file);

        assertEquals("test.jpg", result);
        assertTrue(Files.exists(Paths.get("images//test.jpg")));

        // Clean up the created file
        Files.deleteIfExists(Paths.get("images//test.jpg"));
    }

    @Test
    void deleteImage() throws IOException {
        String fileName = "testDelete.jpg";
        Path path = Paths.get("images//" + fileName);
        Files.createFile(path);

        UploadFileService service = new UploadFileService();
        service.deleteImage(fileName);

        assertFalse(Files.exists(path));
    }
}