package com.enrtreprise.api.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.
import com.enrtreprise.api.repository.LeaveRepository;
import com.enrtreprise.api.model.Leave;
import java.time.LocalDateTime;
import lombok.Data; //import de l'annotation lombok pour générer les getters et setters automatiquement.
import java.util.Optional ; //import de la classe Optional de Java pour gérer les valeurs pouvant être nulles.
@Data // Génère les getters et setters pour les champs de la classe automatiquement.
@Service // Indique que cette classe est un service métier dans le contexte Spring.
public class LeaveService {
    
    // Ajoutez ici les méthodes et logiques liées aux congés
    @Autowired
    private LeaveRepository leaveRepository;

    public Iterable<Leave> getLeaves() {
        return leaveRepository.findAll();
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
    public Optional<Leave> getLeaveById(String id) {
        return leaveRepository.findById(id);
    }

    public Iterable<Leave> getLeavesByEmployeeId(String employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    public Leave approveLeave(String id) {
        Optional<Leave> opt = leaveRepository.findById(id);
        if (!opt.isPresent()) {
            throw new RuntimeException("Leave not found with id: " + id);
        }
        Leave leave = opt.get();
        leave.setStatus(Leave.Status.APPROUVE);
        leave.setCommentaireManager("demande de congé Approuvé");
        return leaveRepository.save(leave);
    }

    public Leave rejectLeave(String id, String commentaireManager) {
        Optional<Leave> opt = leaveRepository.findById(id);
        if (!opt.isPresent()) {
            throw new RuntimeException("Leave not found with id: " + id);
        }
        Leave leave = opt.get();
        leave.setStatus(Leave.Status.REFUSE);
        leave.setCommentaireManager(commentaireManager);
        return leaveRepository.save(leave);
    }
    public Iterable<Leave> getAllPendingLeaves() {
        return leaveRepository.findByStatus(Leave.Status.ATTENTE);
    }
    public Iterable<Leave> getAllApprovedLeaves() {
        return leaveRepository.findByStatus(Leave.Status.APPROUVE);
    }
    public Iterable<Leave> getAllRejectedLeaves() {
        return leaveRepository.findByStatus(Leave.Status.REFUSE);
    }
}
