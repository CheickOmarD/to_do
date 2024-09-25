package com.technologia.to_do.dto;


import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AuthentificationRequest {

    private String email;
    private String password;
    private String devise;
    private String latitude;
    private String longitude;
}
