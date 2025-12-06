package com.enrtreprise.api.repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;//import Spring Data qui fournit des méthodes CRUD de base pour l'entité Employee.
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; //import l'annotation Repository de Spring.
import com.enrtreprise.api.model.Leave; //import de la classe Employee située dans le package model.

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
@Repository

public interface LeaveRepository extends CrudRepository<Leave, String> {
    
    List<Leave> findByEmployeeId(String employeeId);
    
    Iterable<Leave> findByStatus(Leave.Status status);
    @Modifying
    @Query(value ="DELETE FROM conge WHERE employee_id = :id", nativeQuery = true)

    int  deleteByEmployeeId(@Param("id") String id);
}
