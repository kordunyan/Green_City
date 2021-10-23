package com.trash.green.city.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.trash.green.city.domain.Osbb} entity.
 */
public class OsbbDTO implements Serializable {

    private Long id;

    private String address;

    private String geo;

    private String name;

    private TrashCompanyDTO trashCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrashCompanyDTO getTrashCompany() {
        return trashCompany;
    }

    public void setTrashCompany(TrashCompanyDTO trashCompany) {
        this.trashCompany = trashCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OsbbDTO)) {
            return false;
        }

        OsbbDTO osbbDTO = (OsbbDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, osbbDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OsbbDTO{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", geo='" + getGeo() + "'" +
            ", name='" + getName() + "'" +
            ", trashCompany=" + getTrashCompany() +
            "}";
    }
}
