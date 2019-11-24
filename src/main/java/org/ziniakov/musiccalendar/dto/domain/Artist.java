package org.ziniakov.musiccalendar.dto.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;

    @Column(columnDefinition = "text")
    private String uri;

    @Column(columnDefinition = "text")
    private String displayName;

}
