package com.dcu.demo.Product;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    public String imageSave(MultipartFile image) throws IOException{
        Files.createDirectories(Paths.get("./upload/images"));
        String fileName= UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get("./upload/images/"+ fileName);
        Files.copy(image.getInputStream(),filePath);
        return "/upload/images/" + fileName;
    }

    public void fileDelete(String file) throws IOException {
        Files.deleteIfExists(Paths.get("./upload/images").resolve(file));
    }
}
