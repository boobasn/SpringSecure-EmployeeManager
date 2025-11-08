package com.enrtreprise.api.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;//import Spring Data qui fournit des méthodes CRUD de base pour l'entité Employee.
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; //import l'annotation Repository de Spring.

import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.model.Manager; //import de la classe Employee située dans le package model.


@Repository 
public interface ManagerRepository extends CrudRepository<Manager, Long>  {
    
    @Query(value ="SELECT * FROM managers WHERE first_name = :first_name  AND last_name = :last_name", nativeQuery = true)
    Employee findByFirstname(@Param("first_name") String first_name , @Param("last_name") String last_name);
}
