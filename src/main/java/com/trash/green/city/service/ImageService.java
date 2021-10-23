package com.trash.green.city.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Value("${java.io.tmpdir}")
    private String tempPath;

    public List<String> saveImages(List<MultipartFile> images) {
        List<String> result = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                result.add(saveImage(image));
            } catch (IOException e) {
                throw new IllegalStateException("Failed to save image: " + image.getOriginalFilename(), e);
            }
        }
        return result;
    }

    public String saveImage(MultipartFile imageFile) throws IOException {
        byte[] bytes = imageFile.getBytes();

        Path tempImagePath = Paths.get(tempPath, imageFile.getOriginalFilename());
        String absolutePath = tempImagePath.toAbsolutePath().toString();
        Files.write(tempImagePath, bytes);

        String format = absolutePath.substring(absolutePath.lastIndexOf(".") + 1);

        String hashName = generateNameByContent(new File(absolutePath), format);

        Path targetImagePath = Paths.get(Paths.get(".").toAbsolutePath().toString(), "/src/main/resources/static/photos/", hashName);
        Files.copy(tempImagePath, targetImagePath, StandardCopyOption.REPLACE_EXISTING);
        return hashName;
    }

    private String generateNameByContent(File file, String format) throws IOException {
        try (InputStream input = new FileInputStream(file)) {
            return DigestUtils.md5Hex(input) + "." + format.toLowerCase();
        }
    }
}
