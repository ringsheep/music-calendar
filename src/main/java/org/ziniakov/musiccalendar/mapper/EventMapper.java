package org.ziniakov.musiccalendar.mapper;

import org.mapstruct.Mapper;
import org.ziniakov.musiccalendar.dto.domain.Event;

import static org.mapstruct.ReportingPolicy.WARN;

@Mapper(unmappedTargetPolicy = WARN)
public interface EventMapper {
        Event mapToDomainEvent(org.ziniakov.musiccalendar.dto.songkick.Event event);
}
