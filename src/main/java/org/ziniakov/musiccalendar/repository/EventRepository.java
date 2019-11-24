package org.ziniakov.musiccalendar.repository;

import org.ziniakov.musiccalendar.dto.domain.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findAll(int perPage, int offset);
    List<Event> saveAll(List<Event> events);
}
