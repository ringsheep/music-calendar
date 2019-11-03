package org.ziniakov.musiccalendar.mapper;

import org.mapstruct.Mapper;
import org.ziniakov.musiccalendar.dto.jpa.Artist;

import static org.mapstruct.ReportingPolicy.WARN;

@Mapper(unmappedTargetPolicy = WARN)
public interface ArtistMapper {
    Artist mapToJpaArtist(org.ziniakov.musiccalendar.dto.songkick.Artist artist);
}
