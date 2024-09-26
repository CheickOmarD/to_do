package com.technologia.to_do.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;
    private boolean revoked;
    private boolean expired;
    private LocalDateTime createdAt;
    private LocalDateTime logout;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Users users;

    public void setRevoked(boolean b) {
    }

    public void setLogoutAt(LocalDateTime now) {

    }
}
