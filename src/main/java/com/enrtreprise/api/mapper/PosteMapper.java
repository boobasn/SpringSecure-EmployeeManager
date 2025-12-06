package com.enrtreprise.api.mapper;
import com.enrtreprise.api.model.Poste;
import com.enrtreprise.api.dto.PosteDTO;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class PosteMapper {
    public static PosteDTO toDTO(Poste poste)
    {
        PosteDTO dto= new PosteDTO();
        dto.setId(poste.getId());
        dto.setTitre(poste.getTitre());
        dto.setDescription(poste.getDescription());
        return dto;
    }

    public static Poste toEntity(PosteDTO dto)
    {
        Poste poste = new Poste();
        poste.setId(dto.getId());
        poste.setTitre(dto.getTitre());
        poste.setDescription(dto.getDescription());
        return poste ;
    }
}
