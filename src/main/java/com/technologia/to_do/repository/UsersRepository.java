package com.technologia.to_do.repository;

import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UsersRepository extends JpaRepository<Users,Long> {
Users findByIdAndStatut(Long id, Statut statut);
Users findByEmailAndStatut(String email , Statut statut);
Users findByIdAndStatutNot(Long id, Statut statut);
List<Users> findByStatut(Statut statut);
List<Users> findByStatutNot(Statut statut);
Users findByPhoneNumber(String phoneNumber);
List<Users> findByEmailAndStatutNot(String email , Statut statut);
}
