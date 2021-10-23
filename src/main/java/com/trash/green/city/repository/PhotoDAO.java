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

    @Autowired
    private PhotoRepository photoRepository;

    public void savePhotoImage(PhotoEntity photo, MultipartFile imageFile) throws Exception {
        // this gets us to src/main/resources without knowing the full path (hardcoding)
        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();
        photo.setBucket(absolutePath + "/src/main/resources/static/photos/");
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(photo.getBucket() + imageFile.getOriginalFilename());
        Files.write(path, bytes);
        photoRepository.save(photo);
    }
}
