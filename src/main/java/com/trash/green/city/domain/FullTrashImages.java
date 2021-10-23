package com.trash.green.city.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A FullTrashImages.
 */
@Entity
@Table(name = "full_trash_images")
public class FullTrashImages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JsonIgnoreProperties(value = { "osbb", "fullTrashImages", "emptyTrashImages" }, allowSetters = true)
    private TrashExportation trashExportation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FullTrashImages id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return this.path;
    }

    public FullTrashImages path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public TrashExportation getTrashExportation() {
        return this.trashExportation;
    }

    public void setTrashExportation(TrashExportation trashExportation) {
        this.trashExportation = trashExportation;
    }

    public FullTrashImages trashExportation(TrashExportation trashExportation) {
        this.setTrashExportation(trashExportation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FullTrashImages)) {
            return false;
        }
        return id != null && id.equals(((FullTrashImages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FullTrashImages{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            "}";
    }
}
