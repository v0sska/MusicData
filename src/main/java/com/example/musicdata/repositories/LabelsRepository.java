package com.example.musicdata.repositories;

import com.example.musicapiinfo.entities.Labels;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelsRepository extends CrudRepository<Labels, Long> {

}
