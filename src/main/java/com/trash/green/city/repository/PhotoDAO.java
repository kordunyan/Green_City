package com.trash.green.city.repository;

import com.trash.green.city.domain.PhotoEntity;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PhotoDAO {

    public String savePhotoImage(MultipartFile imageFile) throws Exception {
        // this gets us to src/main/resources without knowing the full path (hardcoding)
        Path currentPath = Paths.get(".");
        Path absolutePath = Paths.get(Paths.get(".").toAbsolutePath().toString(), "/src/main/resources/static/photos/");
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(absolutePath.toString(), imageFile.getOriginalFilename());
        Files.write(path, bytes);
        return imageFile.getOriginalFilename();
    }
}
