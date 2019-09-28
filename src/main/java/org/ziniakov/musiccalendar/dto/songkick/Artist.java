package org.ziniakov.musiccalendar.dto.songkick;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Artist {

    @JsonProperty
    String id;

    @JsonProperty
    String uri;

    @JsonProperty
    String displayName;
}
