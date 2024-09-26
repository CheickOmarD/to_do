package com.technologia.to_do.models;

import com.technologia.to_do.enums.Statut;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany (fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private Statut statut = Statut.ACTIVATED;
}
