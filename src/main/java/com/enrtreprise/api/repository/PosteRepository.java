package com.enrtreprise.api.repository;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;//import Spring Data qui fournit des méthodes CRUD de base pour l'entité Employee.
import org.springframework.stereotype.Repository; //import l'annotation Repository de Spring.

import com.enrtreprise.api.model.Poste; //import de la classe Employee située dans le package model.

@Repository
public interface PosteRepository extends CrudRepository<Poste, String> {
    Optional<Poste> findByTitre(String titre);
}
