package com.trash.green.city.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
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

    @Column(name = "action")
    private String action;

    @JsonIgnoreProperties(value = { "trashCompany" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Osbb osbb;

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

    public String getAction() {
        return this.action;
    }

    public TrashExportation action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
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
            ", action='" + getAction() + "'" +
            "}";
    }
}
