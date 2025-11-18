package com.enrtreprise.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.enrtreprise.api.model.Department;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartementDTO {
    private String id;
    private String name;
    private String description;
    

}
