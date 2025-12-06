package com.enrtreprise.api.mapper;

import com.enrtreprise.api.dto.EmployeeDTO;
import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.model.Poste;
import com.enrtreprise.api.model.Department;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setTelephone(employee.getTelephone());
        dto.setMatricule(employee.getMatricule());
        dto.setSalaire(employee.getSalaire());
        if (employee.getPoste() != null) dto.setPoste(employee.getPoste());
        if (employee.getDepartement() != null) dto.setDepartement(employee.getDepartement());
        dto.setDateEmbauche(employee.getDateEmbauche());
        dto.setSexe(employee.getSexe());
        dto.setAdresse(employee.getAdresse());
        if (employee.getUser() != null) dto.setUser(employee.getUser());
       // if (employee.getManager() != null) dto.setManagerId(employee.getManager().getId());
        return dto;
    }

    public static Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setSalaire(dto.getSalaire());
        employee.setTelephone(dto.getTelephone());
        employee.setDateEmbauche(dto.getDateEmbauche());
        employee.setSexe(dto.getSexe());
        employee.setMatricule(dto.getMatricule());
        employee.setAdresse(dto.getAdresse());
        // user et manager seront reliés dans le service
        if (dto.getUser() != null) employee.setUser(dto.getUser());
        if (dto.getPoste() != null) employee.setPoste(dto.getPoste());
        if (dto.getDepartement()!= null) employee.setDepartement(dto.getDepartement());
        return employee;
    }
}
