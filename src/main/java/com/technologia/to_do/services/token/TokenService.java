package com.technologia.to_do.services.token;

import com.technologia.to_do.models.Token;
import com.technologia.to_do.models.Users;

public interface TokenService {
    Token save(Users users, String token);
    void  revokedAllUsersTokens(Users users);
}
