package com.trash.green.city.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.trash.green.city.domain.EmptyTrashImages} entity.
 */
public class EmptyTrashImagesDTO implements Serializable {

    private Long id;

    private String path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmptyTrashImagesDTO)) {
            return false;
        }

        EmptyTrashImagesDTO emptyTrashImagesDTO = (EmptyTrashImagesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, emptyTrashImagesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmptyTrashImagesDTO{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            "}";
    }
}
