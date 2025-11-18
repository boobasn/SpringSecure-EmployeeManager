package com.enrtreprise.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;//import Spring Data qui fournit des méthodes CRUD de base pour l'entité Employee.
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; //import l'annotation Repository de Spring.
import com.enrtreprise.api.model.Department; //import de la classe Employee située dans le package model.
import com.enrtreprise.api.model.Employee;
import java.util.Optional ;
@Repository
public interface DepartmentRepository extends CrudRepository<Department, String> {
       
    @Query(value ="SELECT  * FROM employees WHERE departement_id = :departmentId ", nativeQuery = true)
    Iterable<Employee> findAllEmployee(@Param("departmentId") String departmentId );
    Optional<Department> findByName(String name);
}
