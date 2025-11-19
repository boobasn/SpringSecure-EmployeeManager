package com.enrtreprise.api.mapper;
import com.enrtreprise.api.dto.LeaveDTO;
import com.enrtreprise.api.model.Leave;

public class LeaveMapper {
    
    public static LeaveDTO toDTO(Leave leave) {
        LeaveDTO leaveDTO = new LeaveDTO();
        leaveDTO.setId(leave.getId());
        leaveDTO.setEmployeeId(leave.getEmployee().getId());
        leaveDTO.setStartDate(leave.getDateDebut());
        leaveDTO.setEndDate(leave.getDateFin());
        leaveDTO.setMotif(leave.getMotif());
        leaveDTO.setStatus(leave.getStatus());
        return leaveDTO;
    }

    public static Leave toEntity(LeaveDTO leaveDTO) {
        Leave leave = new Leave();
        leave.setId(leaveDTO.getId());
        leave.setDateDebut(leaveDTO.getStartDate());
        leave.setDateFin(leaveDTO.getEndDate());
        leave.setMotif(leaveDTO.getMotif());
        leave.setStatus(leaveDTO.getStatus());
        return leave;
    }
}
