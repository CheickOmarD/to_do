package com.technologia.to_do.repository;

import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
    Role findByIdAndStatut(Long id, Statut statut);
    Role findByNameAndStatut(String SuperAdmin,Statut statut);
    List<Role> findByStatut(Statut statut);
    List<Role> findByStatutNot(Statut statut);
}
