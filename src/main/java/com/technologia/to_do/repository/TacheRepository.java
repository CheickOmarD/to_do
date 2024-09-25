package com.technologia.to_do.repository;

import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.enums.Type;
import com.technologia.to_do.models.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache,Long> {
    List<Tache> findByStatut(Statut statut);
    List<Tache> findCreatedByIdOrAssignToIdAndStatut
            (Long createdBy_Id, Long assignTo_Id,Statut statut);
    List<Tache> findCreatedByIdOrAssignToIdAndTypeAndStatut
            (Long createdBy_Id, Long assignTo_Id, Type type,Statut statut);
    Tache findByIdAndStatut(Long id, Statut statut);
}
