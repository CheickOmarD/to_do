package com.technologia.to_do.dto;


import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.models.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthentificationResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Role> roles;
    private LocalDateTime createdAt;
    private Statut statut;
    private String accessToken;
}

