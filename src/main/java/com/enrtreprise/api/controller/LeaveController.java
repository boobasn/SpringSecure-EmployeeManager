package com.enrtreprise.api.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize; // Pour sécuriser les méthodes
import com.enrtreprise.api.model.Leave;
import com.enrtreprise.api.service.LeaveService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/leaves")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @GetMapping("/employee/{employeeId}")
    public Iterable<Leave> getLeavesByEmployeeId(@PathVariable Long employeeId) {
        return leaveService.getLeavesByEmployeeId(employeeId);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') or @securityService.isSelfEmployee(#id)")
    public Leave getLeaveById(@PathVariable Long id) {
        return leaveService.getLeaveById(id).orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public Leave createLeave(@RequestBody Leave leave) {
        return leaveService.saveLeave(leave);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public Leave updateLeave(@PathVariable Long id, @RequestBody Leave leave) {
        return leaveService.updateLeave(id, leave);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")   
    public void deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') ")
    @PostMapping("/{id}/approve")
    public Leave approveLeave(@PathVariable Long id) {
        return leaveService.approveLeave(id);
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public Leave rejectLeave(@PathVariable Long id, @RequestBody String commentaireManager) {
        return leaveService.rejectLeave(id, commentaireManager);
    }
}
