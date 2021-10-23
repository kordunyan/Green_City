package com.trash.green.city.service;

import com.trash.green.city.domain.PhotoEntity;
import com.trash.green.city.repository.PhotoDAO;
import com.trash.green.city.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private PhotoDAO photoRepository;

    public void saveImage(MultipartFile imageFile, PhotoEntity photoDTO) throws Exception {
        //photoRepository.savePhotoImage(photoDTO, imageFile);
    }
}
