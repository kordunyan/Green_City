package com.trash.green.city.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import lombok.Data;

/**
 * A TrashExportation.
 */
public class TrashExportationReportWithType implements Serializable {

    private String address;

    private String name;
    private Integer plastic;
    private Integer mixed;
    private Integer organic;
    private Integer paper;

    public TrashExportationReportWithType(String address, String name) {
        this.address = address;
        this.name = name;
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

    public Integer getPlastic() {
        return plastic;
    }

    public void setPlastic(Integer plastic) {
        this.plastic = plastic;
    }

    public Integer getMixed() {
        return mixed;
    }

    public void setMixed(Integer mixed) {
        this.mixed = mixed;
    }

    public Integer getOrganic() {
        return organic;
    }

    public void setOrganic(Integer organic) {
        this.organic = organic;
    }

    public Integer getPaper() {
        return paper;
    }

    public void setPaper(Integer paper) {
        this.paper = paper;
    }
}
