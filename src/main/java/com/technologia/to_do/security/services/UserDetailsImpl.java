package com.technologia.to_do.security.services;

import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.models.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter @Setter
public class UserDetailsImpl implements UserDetails {
    private Long Id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String phoneNumber;
    private  String password;
    private LocalDateTime createAt;
    private Statut statut;
    private   Collection<? extends  GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String firstName,
                           String lastName, String email,
                           String phoneNumber, String password,
                           LocalDateTime createAt, Statut statut,
                           Collection<? extends GrantedAuthority> roles) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.createAt = createAt;
        this.statut = statut;
        this.authorities = roles;
    }
    public static  UserDetailsImpl build(Users person) {
        List<GrantedAuthority> roles = person.getRoles().stream()
                .map(appRole -> new SimpleGrantedAuthority(appRole.getName()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getPhoneNumber(),
                person.getPassword(),
                person.getCreatedAt(),
                person.getStatut(),
                roles
        );
    }
    public static UserDetailsImpl build (Long id, String firstName,
                           String lastName, String email,
                           String phoneNumber, String password,
                           LocalDateTime createAt, Statut statut,
                           Collection<? extends GrantedAuthority> roles){
        return new UserDetailsImpl(id, firstName, lastName, email,
                phoneNumber, password, createAt, statut, roles);
    }



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean getCreatedAt() {
        return true;
    }
}
