package org.ziniakov.musiccalendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ziniakov.musiccalendar.dto.jpa.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, String> {
}
