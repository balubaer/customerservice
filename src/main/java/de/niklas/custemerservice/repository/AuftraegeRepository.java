package de.niklas.custemerservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.niklas.custemerservice.model.Auftraege;

@Repository
public interface AuftraegeRepository extends CrudRepository<Auftraege, String> {

}
