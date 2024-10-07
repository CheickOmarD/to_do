package com.technologia.to_do.services.users;

import com.technologia.to_do.dto.RoleResponse;
import com.technologia.to_do.dto.UsersResponse;
import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.exceptions.AlreadyExistException;
import com.technologia.to_do.exceptions.NotFoundException;
import com.technologia.to_do.models.Users;
import com.technologia.to_do.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsersResponse save(Users users) {
        Users phoneNumber = usersRepository.findByPhoneNumber(users.getPhoneNumber().trim());
        if (phoneNumber != null) {
            throw new AlreadyExistException("Le numéro de téléphone est déjà utilisé.");
        }
        Users email = usersRepository
                 .findByEmailAndStatutNot(users.getEmail(), Statut.DELETE);
        if (email != null) {
            throw new AlreadyExistException("Cette adresse email est déjà utilisée.");
        }
        if (users.getPassword() != null && !users.getPassword().isEmpty()) {
            users.setPassword(passwordEncoder.encode(users.getPassword()));
        }
        return mapToResponse(usersRepository.save(users));
    }

    @Override
    public UsersResponse findByIdAndStatut(Long id, Statut statut) {
        Users users = usersRepository.findByIdAndStatut(id, statut);
        if (users == null) {
            throw new NotFoundException("Cet utilisateur n'existe pas.");
        }
        return mapToResponse(users);
    }

    @Override
    public List<UsersResponse> findByStatut(Statut statut) {
        List<Users> users = usersRepository.findByStatut(statut);
        users.sort(Comparator.comparing(Users::getId).reversed());
        return mapToResponse(users);
    }

    @Override
    public List<UsersResponse> findByStatutNot(Statut statut) {
        List<Users> users = usersRepository.findByStatutNot(statut);
        users.sort(Comparator.comparing(Users::getId).reversed());
        return mapToResponse(users);
    }

    @Override
    public UsersResponse update(Users users) {
        Users userToUpdate = usersRepository
                .findByIdAndStatutNot(users.getId(), Statut.ACTIVATED);
        if (userToUpdate == null) {
            throw new NotFoundException("Cet utilisateur n'existe pas.");
        }
        userToUpdate.setFirstName(users.getFirstName());
        userToUpdate.setLastName(users.getLastName());
        userToUpdate.setEmail(users.getEmail());
        userToUpdate.setPassword(users.getPassword());
        userToUpdate.setPhoneNumber(users.getPhoneNumber());
        return mapToResponse(usersRepository.save(userToUpdate));
    }

    @Override
    public void delete(Long id) {
        Users users = usersRepository.findByIdAndStatutNot(id, Statut.DELETE);
        if (users == null) {
            throw new NotFoundException("Cet utilisateur n'existe pas.");
        }
        users.setStatut(Statut.DELETE);
    }

    @Override
    public UsersResponse activate(Users users) {
        Users userToActivate = usersRepository
                .findByIdAndStatutNot(users.getId(), Statut.DELETE);
        if (userToActivate == null) {
            throw new NotFoundException("Cet utilisateur n'existe pas.");
        }
        userToActivate.setStatut(Statut.ACTIVATED);
        return mapToResponse(usersRepository.save(userToActivate));
    }

    @Override
    public UsersResponse mapToResponse(Users users) {
        List<RoleResponse> roles = new ArrayList<>();
        if (users.getRoles() != null && !users.getRoles().isEmpty()) {
            roles = users.getRoles().stream()
                    .map(role -> RoleResponse.builder()
                            .id(role.getId())
                            .name(role.getName())
                            .build()).toList();
        }
        return UsersResponse.builder()
                .id(users.getId())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .email(users.getEmail())
                .phoneNumber(users.getPhoneNumber())
                .roles(roles)
                .statut(users.getStatut())
                .build();
    }

    @Override
    public List<UsersResponse> mapToResponse(List<Users> users) {
        List<UsersResponse> usersResponse = new ArrayList<>();
        for (Users user : users) {
            usersResponse.add(mapToResponse(user));
        }
        return usersResponse;
    }
}

