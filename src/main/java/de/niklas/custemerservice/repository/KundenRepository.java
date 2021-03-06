package de.niklas.custemerservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.niklas.custemerservice.model.Kunde;

@Repository
public interface KundenRepository extends CrudRepository<Kunde, Long>  {
	

}
