package com.example.musicdata.repositories;

import com.example.musicdata.entities.MusicGroups;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicGroupsRepository extends CrudRepository<MusicGroups, Long> {

    @Query("SELECT mg FROM MusicGroups mg JOIN FETCH mg.labels WHERE mg.id = :id")
    List<MusicGroups> findByMusicGroupById(@Param("id") Long id);


}
