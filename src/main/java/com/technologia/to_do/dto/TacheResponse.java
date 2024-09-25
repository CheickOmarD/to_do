package com.technologia.to_do.dto;


import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.enums.Type;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TacheResponse {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private UsersResponse createdBy;
    private UsersResponse assignTo;
    private Statut statut;
    private Type type;
}
