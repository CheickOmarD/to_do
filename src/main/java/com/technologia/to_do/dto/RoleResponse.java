package com.technologia.to_do.dto;


import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RoleResponse {

    private Long id;
    private String name;
}
