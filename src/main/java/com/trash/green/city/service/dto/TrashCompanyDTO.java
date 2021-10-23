package com.trash.green.city.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.trash.green.city.domain.TrashCompany} entity.
 */
public class TrashCompanyDTO implements Serializable {

    private Long id;

    private String name;

    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrashCompanyDTO)) {
            return false;
        }

        TrashCompanyDTO trashCompanyDTO = (TrashCompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trashCompanyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrashCompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
