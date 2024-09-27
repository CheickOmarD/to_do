package com.technologia.to_do.repository;

import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.enums.Type;
import com.technologia.to_do.models.Tache;
import com.technologia.to_do.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
public interface TacheRepository extends JpaRepository<Tache,Long> {

    List<Tache> findByStatut(Statut statut);
    Tache findByIdAndStatut(Long id, Statut statut);
    List<Tache> findByCreatedByOrAssignToAndStatut(Users createdBy, Users assignTo, Statut statut);
    List<Tache> findByCreatedByIdOrAssignToIdAndTypeAndStatut
    (Long createdById, Long assignToId, Type type, Statut statut);
    List<Tache> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Tache> findByAssignToIdAndCreatedAtBetween(Long assignToId, LocalDateTime startDate, LocalDateTime endDate);

}
