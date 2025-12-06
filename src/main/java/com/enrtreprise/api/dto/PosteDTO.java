package com.enrtreprise.api.dto;
import com.enrtreprise.api.model.Poste ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosteDTO {
    private String id ;
    private String titre ;
    private String description ;
}
