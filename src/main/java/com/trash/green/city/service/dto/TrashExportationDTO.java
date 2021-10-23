package com.trash.green.city.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.trash.green.city.domain.TrashExportation} entity.
 */
public class TrashExportationDTO implements Serializable {

    private Long id;

    private Integer weight;

    private ZonedDateTime date;

    private String trash_type;

    private String action;

    private Boolean is_wash;

    private OsbbDTO osbb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getTrash_type() {
        return trash_type;
    }

    public void setTrash_type(String trash_type) {
        this.trash_type = trash_type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean getIs_wash() {
        return is_wash;
    }

    public void setIs_wash(Boolean is_wash) {
        this.is_wash = is_wash;
    }

    public OsbbDTO getOsbb() {
        return osbb;
    }

    public void setOsbb(OsbbDTO osbb) {
        this.osbb = osbb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrashExportationDTO)) {
            return false;
        }

        TrashExportationDTO trashExportationDTO = (TrashExportationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trashExportationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrashExportationDTO{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", date='" + getDate() + "'" +
            ", trash_type='" + getTrash_type() + "'" +
            ", action='" + getAction() + "'" +
            ", is_wash='" + getIs_wash() + "'" +
            ", osbb=" + getOsbb() +
            "}";
    }
}
