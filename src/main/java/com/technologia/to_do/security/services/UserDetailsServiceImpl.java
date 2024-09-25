package com.technologia.to_do.security.services;

import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.exceptions.NotFoundException;
import com.technologia.to_do.models.Users;
import com.technologia.to_do.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = repository.findByEmailAndStatut(email, Statut.ACTIVATED);
        if (users == null){
            throw new NotFoundException("Email ou mot de passe incorrect");
        }
        return UserDetailsImpl.build(users);
    }
}
