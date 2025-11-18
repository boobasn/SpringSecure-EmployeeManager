package com.enrtreprise.api.repository; //déclaration du package repository dans lequel se trouve l'interface EmployeeRepository.


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;//import Spring Data qui fournit des méthodes CRUD de base pour l'entité Employee.
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; //import l'annotation Repository de Spring.

import com.enrtreprise.api.model.Employee; //import de la classe Employee située dans le package model.

@Repository //annotation Spring pour indiquer que la classe est un bean, et que son rôle est de communiquer avec une source de données (en l'occurrence la base de données).
public interface EmployeeRepository extends CrudRepository<Employee, String> {
    @Query(value ="SELECT * FROM employees WHERE first_name = :first_name  AND last_name = :last_name", nativeQuery = true)
    Employee findByFirstname(@Param("first_name") String first_name , @Param("last_name") String last_name);

    boolean existsByEmail(String email);


}
