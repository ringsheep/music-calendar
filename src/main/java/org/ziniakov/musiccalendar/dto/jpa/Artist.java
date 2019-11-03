package org.ziniakov.musiccalendar.dto.jpa;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Artist {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;

    @Column(columnDefinition = "text")
    private String uri;

    @Column(columnDefinition = "text")
    private String displayName;

}
