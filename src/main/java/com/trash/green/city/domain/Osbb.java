package com.trash.green.city.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Osbb.
 */
@Entity
@Table(name = "osbb")
public class Osbb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "geo")
    private String geo;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private TrashCompany trashCompany;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Osbb id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public Osbb address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeo() {
        return this.geo;
    }

    public Osbb geo(String geo) {
        this.setGeo(geo);
        return this;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getName() {
        return this.name;
    }

    public Osbb name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrashCompany getTrashCompany() {
        return this.trashCompany;
    }

    public void setTrashCompany(TrashCompany trashCompany) {
        this.trashCompany = trashCompany;
    }

    public Osbb trashCompany(TrashCompany trashCompany) {
        this.setTrashCompany(trashCompany);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Osbb)) {
            return false;
        }
        return id != null && id.equals(((Osbb) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Osbb{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", geo='" + getGeo() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
