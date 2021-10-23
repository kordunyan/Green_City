package com.trash.green.city.service.exportation;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class ExportTrashDto {

    private Integer containerCount;
    private String trashType;
    private Boolean isWash;
    private Long osbbId;
    private List<MultipartFile> emptyTrashImages;
    private List<MultipartFile> fullTrashImages;

    public Integer getContainerCount() {
        return containerCount;
    }

    public void setContainerCount(Integer containerCount) {
        this.containerCount = containerCount;
    }

    public String getTrashType() {
        return trashType;
    }

    public void setTrashType(String trashType) {
        this.trashType = trashType;
    }

    public Boolean getWash() {
        return isWash;
    }

    public void setWash(Boolean wash) {
        isWash = wash;
    }

    public Long getOsbbId() {
        return osbbId;
    }

    public void setOsbbId(Long osbbId) {
        this.osbbId = osbbId;
    }

    public List<MultipartFile> getEmptyTrashImages() {
        return emptyTrashImages;
    }

    public void setEmptyTrashImages(List<MultipartFile> emptyTrashImages) {
        this.emptyTrashImages = emptyTrashImages;
    }

    public List<MultipartFile> getFullTrashImages() {
        return fullTrashImages;
    }

    public void setFullTrashImages(List<MultipartFile> fullTrashImages) {
        this.fullTrashImages = fullTrashImages;
    }
}
