package com.technologia.to_do.services.authentificaion;

import com.technologia.to_do.dto.AuthentificationRequest;
import com.technologia.to_do.dto.AuthentificationResponse;
import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.exceptions.NotFoundException;
import com.technologia.to_do.models.Role;
import com.technologia.to_do.models.Users;
import com.technologia.to_do.repository.RoleRepository;
import com.technologia.to_do.repository.UsersRepository;
import com.technologia.to_do.security.jwt.JwtService;
import com.technologia.to_do.security.services.UserDetailsImpl;
import com.technologia.to_do.services.token.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthentificationServiceImpl implements AuthentificationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final UsersRepository usersRepository;

    @Override
    public AuthentificationResponse authentificate(AuthentificationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            Users author = getAuthor();
            if (author != null) {
                tokenService.revokedAllUsersTokens(author);
                tokenService.save(author, token);
            }

            return new AuthentificationResponse(
                    userDetails.getId(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getEmail(),
                    userDetails.getPhoneNumber(),
                    new ArrayList<>(),
                    userDetails.getCreateAt(),
                    userDetails.getStatut(),
                    token
            );
        } catch (BadCredentialsException e) {
            throw new NotFoundException("Email ou mot de passe incorrect");
        }
    }

    public Users getAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return usersRepository.findByEmailAndStatut(email, Statut.ACTIVATED);
        }
        return null;
    }

    Role getRoleByName(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            throw new NotFoundException("Role incorrect");
        }
        return role;
    }
}
