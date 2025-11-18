package com.enrtreprise.api.mapper;
import com.enrtreprise.api.dto.ContratDTO;
import com.enrtreprise.api.model.Contrat;
import com.enrtreprise.api.model.Employee;
public class ContratMapper {
    public static ContratDTO toDTO(Contrat contrat) {
        ContratDTO dto = new ContratDTO();
        dto.setId(contrat.getId());
        dto.setDateDebut(contrat.getDateDebut());
        dto.setDateFin(contrat.getDateFin());
        dto.setSalaire(contrat.getSalaire());
        dto.setStatut(contrat.getStatut());
        if (contrat.getEmployee() != null) {
            dto.setEmployeeId(contrat.getEmployee().getId());
        }
        return dto;
    }

    public static Contrat toEntity(ContratDTO dto) {
        Contrat contrat = new Contrat();
        contrat.setId(dto.getId());
        contrat.setDateDebut(dto.getDateDebut());
        contrat.setDateFin(dto.getDateFin());
        contrat.setSalaire(dto.getSalaire());
        contrat.setStatut(dto.getStatut());
        if (dto.getEmployeeId() != null) {
            Employee employee = new Employee();
            employee.setId(dto.getEmployeeId());
            contrat.setEmployee(employee);
        }

        return contrat;
    }
    
}
