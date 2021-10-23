package com.trash.green.city.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A TrashExportation.
 */
@Entity
@Table(name = "trash_exportation")
public class TrashExportation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "trash_type")
    private String trash_type;

    @Column(name = "is_wash")
    private Boolean is_wash;

    @JsonIgnoreProperties(value = { "trashCompany" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Osbb osbb;

    @OneToMany(mappedBy = "trashExportation")
    @JsonIgnoreProperties(value = { "trashExportation" }, allowSetters = true)
    private Set<FullTrashImages> fullTrashImages = new HashSet<>();

    @OneToMany(mappedBy = "trashExportation")
    @JsonIgnoreProperties(value = { "trashExportation" }, allowSetters = true)
    private Set<EmptyTrashImages> emptyTrashImages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TrashExportation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public TrashExportation weight(Integer weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public TrashExportation date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getTrash_type() {
        return this.trash_type;
    }

    public TrashExportation trash_type(String trash_type) {
        this.setTrash_type(trash_type);
        return this;
    }

    public void setTrash_type(String trash_type) {
        this.trash_type = trash_type;
    }

    public Boolean getIs_wash() {
        return this.is_wash;
    }

    public TrashExportation is_wash(Boolean is_wash) {
        this.setIs_wash(is_wash);
        return this;
    }

    public void setIs_wash(Boolean is_wash) {
        this.is_wash = is_wash;
    }

    public Osbb getOsbb() {
        return this.osbb;
    }

    public void setOsbb(Osbb osbb) {
        this.osbb = osbb;
    }

    public TrashExportation osbb(Osbb osbb) {
        this.setOsbb(osbb);
        return this;
    }

    public Set<FullTrashImages> getFullTrashImages() {
        return this.fullTrashImages;
    }

    public void setFullTrashImages(Set<FullTrashImages> fullTrashImages) {
        if (this.fullTrashImages != null) {
            this.fullTrashImages.forEach(i -> i.setTrashExportation(null));
        }
        if (fullTrashImages != null) {
            fullTrashImages.forEach(i -> i.setTrashExportation(this));
        }
        this.fullTrashImages = fullTrashImages;
    }

    public TrashExportation fullTrashImages(Set<FullTrashImages> fullTrashImages) {
        this.setFullTrashImages(fullTrashImages);
        return this;
    }

    public TrashExportation addFullTrashImages(FullTrashImages fullTrashImages) {
        this.fullTrashImages.add(fullTrashImages);
        fullTrashImages.setTrashExportation(this);
        return this;
    }

    public TrashExportation removeFullTrashImages(FullTrashImages fullTrashImages) {
        this.fullTrashImages.remove(fullTrashImages);
        fullTrashImages.setTrashExportation(null);
        return this;
    }

    public Set<EmptyTrashImages> getEmptyTrashImages() {
        return this.emptyTrashImages;
    }

    public void setEmptyTrashImages(Set<EmptyTrashImages> emptyTrashImages) {
        if (this.emptyTrashImages != null) {
            this.emptyTrashImages.forEach(i -> i.setTrashExportation(null));
        }
        if (emptyTrashImages != null) {
            emptyTrashImages.forEach(i -> i.setTrashExportation(this));
        }
        this.emptyTrashImages = emptyTrashImages;
    }

    public TrashExportation emptyTrashImages(Set<EmptyTrashImages> emptyTrashImages) {
        this.setEmptyTrashImages(emptyTrashImages);
        return this;
    }

    public TrashExportation addEmptyTrashImages(EmptyTrashImages emptyTrashImages) {
        this.emptyTrashImages.add(emptyTrashImages);
        emptyTrashImages.setTrashExportation(this);
        return this;
    }

    public TrashExportation removeEmptyTrashImages(EmptyTrashImages emptyTrashImages) {
        this.emptyTrashImages.remove(emptyTrashImages);
        emptyTrashImages.setTrashExportation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrashExportation)) {
            return false;
        }
        return id != null && id.equals(((TrashExportation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrashExportation{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", date='" + getDate() + "'" +
            ", trash_type='" + getTrash_type() + "'" +
            ", is_wash='" + getIs_wash() + "'" +
            "}";
    }
}
