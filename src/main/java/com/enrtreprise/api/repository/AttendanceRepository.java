package com.enrtreprise.api.repository;
import org.springframework.data.repository.CrudRepository;//import Spring Data qui fournit des méthodes CRUD de base pour l'entité Employee.
import org.springframework.stereotype.Repository; //import l'annotation Repository de Spring.

import com.enrtreprise.api.model.Attendance;

@Repository
public interface AttendanceRepository extends CrudRepository<Attendance,Long> {
    
}
