package com.enrtreprise.api.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize; // Pour sécuriser les méthodes
import com.enrtreprise.api.model.Leave;
import org.springframework.http.ResponseEntity;

import com.enrtreprise.api.dto.LeaveDTO;
import com.enrtreprise.api.mapper.LeaveMapper;
import com.enrtreprise.api.service.LeaveService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/leaves")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<LeaveDTO>> getAllLeaves() {
        List<Leave> leaves = leaveService.getLeaves();
        List<LeaveDTO> leaveDTOs = leaves.stream()
                .map(LeaveMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveDTOs);
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveDTO>> getLeavesByEmployeeId(@PathVariable String employeeId) {
        List<Leave> leaves = (List<Leave>) leaveService.getLeavesByEmployeeId(employeeId);
        List<LeaveDTO> leaveDTOs = leaves.stream()
                .map(LeaveMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveDTOs);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') or @securityService.isSelfEmployee(#id)")
    public ResponseEntity<LeaveDTO> getLeaveById(@PathVariable String id) {
            Leave leave = leaveService.getLeaveById(id);
            LeaveDTO leaveDTO = LeaveMapper.toDTO(leave);
            return ResponseEntity.ok(leaveDTO);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<LeaveDTO> createLeave(@RequestBody Leave leave) {
        Leave savedLeave = leaveService.saveLeave(leave);
        LeaveDTO leaveDTO = LeaveMapper.toDTO(savedLeave);
        return ResponseEntity.ok(leaveDTO);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<LeaveDTO> updateLeave(@PathVariable String id, @RequestBody Leave leave) {
        Leave updatedLeave = leaveService.updateLeave(id, leave);
        LeaveDTO leaveDTO = LeaveMapper.toDTO(updatedLeave);
        return ResponseEntity.ok(leaveDTO);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")   
    public void deleteLeave(@PathVariable String id) {
        leaveService.deleteLeave(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') ")
    @PostMapping("/{id}/approve")
    public ResponseEntity<LeaveDTO> approveLeave(@PathVariable String id) {
        Leave approvedLeave = leaveService.approveLeave(id);
        LeaveDTO leaveDTO = LeaveMapper.toDTO(approvedLeave);
        return ResponseEntity.ok(leaveDTO);
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<LeaveDTO> rejectLeave(@PathVariable String id, @RequestBody String commentaireManager) {
        Leave rejectedLeave = leaveService.rejectLeave(id, commentaireManager);
        LeaveDTO leaveDTO = LeaveMapper.toDTO(rejectedLeave);
        return ResponseEntity.ok(leaveDTO);
    }
    @GetMapping("/approved")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<LeaveDTO>> getApprovedLeaves() {
        List<Leave> approvedLeaves = (List<Leave>) leaveService.getAllApprovedLeaves();
        List<LeaveDTO> leaveDTOs = approvedLeaves.stream()
                .map(LeaveMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveDTOs);
    }
    @GetMapping("/rejected")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<LeaveDTO>> getRejectedLeaves() {
        List<Leave> rejectedLeaves = (List<Leave>) leaveService.getAllRejectedLeaves();
        List<LeaveDTO> leaveDTOs = rejectedLeaves.stream()
                .map(LeaveMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveDTOs);
    }
    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<LeaveDTO>> getPendingLeaves() {
        List<Leave> pendingLeaves = (List<Leave>) leaveService.getAllPendingLeaves();
        List<LeaveDTO> leaveDTOs = pendingLeaves.stream()
                .map(LeaveMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveDTOs);
    }
}