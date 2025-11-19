package com.enrtreprise.api.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.
import com.enrtreprise.api.repository.LeaveRepository;
import com.enrtreprise.api.model.Leave;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional ;
import com.enrtreprise.api.exception.ResourceNotFoundException;
import com.enrtreprise.api.exception.BadRequestException;
import lombok.Data; //import de l'annotation lombok pour générer les getters et setters automatiquement.
@Data // Génère les getters et setters pour les champs de la classe automatiquement.
@Service // Indique que cette classe est un service métier dans le contexte Spring.
public class LeaveService {
    
    // Ajoutez ici les méthodes et logiques liées aux congés
    @Autowired
    private LeaveRepository leaveRepository;

    public List<Leave> getLeaves() {
        try {
         return (List<Leave>) leaveRepository.findAll();
        } catch (Exception e) {
            throw new BadRequestException("Erreur lors de la récupération des congés: " + e.getMessage());
        }
    }

    public Leave saveLeave(Leave leave) {
        leave.setStatus(Leave.Status.ATTENTE);
        leave.setDateDemande(LocalDateTime.now());
        return leaveRepository.save(leave);
    }
    public String deleteLeave(String id){
        
        leaveRepository.deleteById(id);
        return "leave supprime avec success" ;
    }
    public Leave updateLeave(String id, Leave updatedLeave){
        Optional<Leave> opt  =  leaveRepository.findById(id);
        if (!opt.isPresent()){
            throw new  RuntimeException("Leave not found with id: " + id);
        }
        Leave leave = opt.get();
        if (updatedLeave.getDateDebut() != null) leave.setDateDebut(updatedLeave.getDateDebut());
        if (updatedLeave.getDateFin() != null) leave.setDateFin(updatedLeave.getDateFin());
        if (updatedLeave.getTypeConge() != null) leave.setTypeConge(updatedLeave.getTypeConge());
        if (updatedLeave.getStatus() != null) leave.setStatus(updatedLeave.getStatus());

        return leaveRepository.save(leave);

    }
    public Leave getLeaveById(String id) {
        Optional<Leave> opt = leaveRepository.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("Leave not found with id: " + id);
        }
        return opt.get();
    }

    public List<Leave> getLeavesByEmployeeId(String employeeId) {
        try {
        return  leaveRepository.findByEmployeeId(employeeId);
        } catch (Exception e) {
            throw e;
        }
    }

    public Leave approveLeave(String id) {
        Optional<Leave> opt = leaveRepository.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("Leave not found with id: " + id);
        }
        Leave leave = opt.get();
        leave.setStatus(Leave.Status.APPROUVE);
        leave.setCommentaireManager("demande de congé Approuvé");
        return leaveRepository.save(leave);
    }

    public Leave rejectLeave(String id, String commentaireManager) {
        Optional<Leave> opt = leaveRepository.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("Leave not found with id: " + id);
        }
        Leave leave = opt.get();
        leave.setStatus(Leave.Status.REFUSE);
        leave.setCommentaireManager(commentaireManager);
        return leaveRepository.save(leave);
    }
    public List<Leave> getAllPendingLeaves() {
        try {
        return (List<Leave>) leaveRepository.findByStatus(Leave.Status.ATTENTE);
        } catch (Exception e) {
            throw e;
        }
    }
    public List<Leave> getAllApprovedLeaves() {
        try {
            return (List<Leave>) leaveRepository.findByStatus(Leave.Status.APPROUVE);
        } catch (Exception e) {
            throw e;
        }
        
    }
    public List<Leave> getAllRejectedLeaves() {
        try {
            return (List<Leave>) leaveRepository.findByStatus(Leave.Status.REFUSE);
        } catch (Exception e) {
            throw e;
        }
    }
}
