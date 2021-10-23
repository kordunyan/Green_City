package com.trash.green.city.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;

/**
 * A TrashExportation.
 */
@Data
public class TrashExportationReport implements Serializable {

    private Integer weight;

    private String address;

    private String name;
    private String trash_type;

    public TrashExportationReport(Integer weight, String address, String name, String trash_type) {
        this.weight = weight;
        this.address = address;
        this.name = name;
        this.trash_type = trash_type;
    }

    public TrashExportationReport() {}

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrash_type() {
        return trash_type;
    }

    public void setTrash_type(String trash_type) {
        this.trash_type = trash_type;
    }
}
