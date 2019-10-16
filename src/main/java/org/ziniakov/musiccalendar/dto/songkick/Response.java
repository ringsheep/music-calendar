package org.ziniakov.musiccalendar.dto.songkick;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response {
    @JsonProperty
    ResultsPage resultsPage;
}
