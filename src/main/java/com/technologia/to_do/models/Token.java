package com.technologia.to_do.models;

import com.sun.tools.javac.parser.JavacParser;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Token {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;
    private boolean rovoked;
    private boolean expired;
    private LocalDateTime createdAt;
    private LocalDateTime logout;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    public void setRevoked(boolean b) {
    }

    public void setLogoutAt(LocalDateTime now) {

    }
}
