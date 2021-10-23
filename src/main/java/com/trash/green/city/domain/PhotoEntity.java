package com.trash.green.city.domain;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "photos")
@Data
public class PhotoEntity {

    @Id
    @GeneratedValue
    private int photoId;

    @Column
    private String bucket;

    @Column
    private String fileName;
}
