package com.technologia.to_do.models;

import com.technologia.to_do.enums.Statut;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @ToString.Exclude
    private List<Users> users = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private Statut statut = Statut.ACTIVATED;

    public Role(String name) {
        this.name = name;
    }

}
