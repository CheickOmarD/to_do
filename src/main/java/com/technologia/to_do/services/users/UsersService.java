package com.technologia.to_do.services.users;

import com.technologia.to_do.dto.UsersResponse;
import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.models.Users;

import java.util.List;

public interface UsersService {

    UsersResponse save(Users users);
    UsersResponse findByIdAndStatut(Long id , Statut statut);
    List<UsersResponse> findByStatut(Statut statut);
    List<UsersResponse> findByStatutNot(Statut statut);
    UsersResponse update(Users users);
    void delete(Long id);
    UsersResponse activate(Users users);
    UsersResponse mapToResponse(Users users);
    List<UsersResponse> mapToResponse(List<Users> users);
}
