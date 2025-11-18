package com.enrtreprise.api.mapper;
import  com.enrtreprise.api.model.Department;
import com.enrtreprise.api.dto.DepartementDTO;
public class DepartementMapper {
    public static DepartementDTO  toDTO(Department departement){
        DepartementDTO dto = new DepartementDTO();
        dto.setId(departement.getId());
        dto.setName(departement.getName());
        dto.setDescription(departement.getDescription());
        return dto;
    }

    public static Department toEntity(DepartementDTO dto){
        Department departement = new Department();
        departement.setId(dto.getId());
        departement.setName(dto.getName());
        departement.setDescription(dto.getDescription());
        return departement;
    }
}
