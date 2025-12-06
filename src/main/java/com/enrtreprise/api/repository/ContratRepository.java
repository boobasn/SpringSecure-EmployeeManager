package com.enrtreprise.api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;//import Spring Data qui fournit des méthodes CRUD de base pour l'entité Employee.
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; //import l'annotation Repository de Spring.
import com.enrtreprise.api.model.Contrat; //import de la classe Employee située dans le package model.

@Repository
public interface ContratRepository extends CrudRepository<Contrat , String> {
    @Modifying
    @Query(value = "DELETE FROM contrats WHERE employee_id = :id", nativeQuery = true)
    int deleteByEmployeeId(@Param("id") String id);
}