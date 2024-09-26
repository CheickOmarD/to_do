package com.technologia.to_do.services.authentificaion;

import com.technologia.to_do.dto.AuthentificationRequest;
import com.technologia.to_do.dto.AuthentificationResponse;
import com.technologia.to_do.models.Users;

public interface AuthentificationService {
    AuthentificationResponse authentificate(AuthentificationRequest authentificationRequest);
    Users getAuthor();

    AuthentificationResponse authenticate(AuthentificationRequest request);
}
