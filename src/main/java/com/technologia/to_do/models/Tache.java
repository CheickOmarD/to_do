package com.technologia.to_do.models;


import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    private Users createdBy;
    @ManyToOne
    private Users assignTo;
    @Enumerated(value = EnumType.STRING)
    private Type type = Type.EN_COURS;
    @Enumerated(value = EnumType.STRING)
    private Statut statut = Statut.ACTIVATED;
}
