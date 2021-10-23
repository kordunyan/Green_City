package com.trash.green.city.service.dto;

import java.util.List;

public class TrashExportationWithImagesDTO extends TrashExportationDTO {

    private List<String> fullTrashImages;

    private List<String> emptyTrashImages;

    public List<String> getFullTrashImages() {
        return fullTrashImages;
    }

    public void setFullTrashImages(List<String> fullTrashImages) {
        this.fullTrashImages = fullTrashImages;
    }

    public List<String> getEmptyTrashImages() {
        return emptyTrashImages;
    }

    public void setEmptyTrashImages(List<String> emptyTrashImages) {
        this.emptyTrashImages = emptyTrashImages;
    }
}
