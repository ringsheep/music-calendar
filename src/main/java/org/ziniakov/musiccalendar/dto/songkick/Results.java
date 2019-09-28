package org.ziniakov.musiccalendar.dto.songkick;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Results {

    @JsonProperty
    List<Artist> artists;
}
