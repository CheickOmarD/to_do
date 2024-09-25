package com.technologia.to_do.security.jwt;

import com.technologia.to_do.exceptions.TokenExpiredException;
import com.technologia.to_do.security.services.UserDetailsImpl;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.logging.Logger;

import static com.technologia.to_do.security.SecurityConstants.*;
import static org.springframework.security.config.Elements.JWT;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    public String extractUsername(String token) {
        return extractClaim(token, Claim::asString);
    }

    public <T> T extractClaim(String token, Function<Claim, T> claimsResolver) {
        // Correction du nom de la classe 'DecodedJWT'
        final DecodedJWT decodedJWT = JWT.decode(token);
        final Claim claim = decodedJWT.getClaim("sub");
        return claimsResolver.apply(claim);
    }

    public String generateJwtToken(Authentication authentication) {
        // Récupération du nom d'utilisateur à partir de UserDetailsImpl
        String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();

        // Assurez-vous que JWT_SECRET_KEY et EXPIRATION_TIME sont bien définis
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
        return JWT.create()
                .withIssuer(GET_COMPANY_NAME) // Remplacer par le nom de votre entreprise ou une constante
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Correction du nom de la méthode
                .sign(algorithm);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        try {
            // Décodage du token
            final DecodedJWT decodedJWT = JWT.decode(token);
            // Récupération de la date d'expiration à partir du token décodé
            final Date expirationDate = decodedJWT.getExpiresAt(); // Correction de la méthode 'getExpiresAt'

            // Vérification si le token est expiré
            if (expirationDate != null && expirationDate.before(new Date())) {
                throw new TokenExpiredException("Le token d'authentification a expiré !");
            }

            // Si le token n'est pas expiré, retourne false
            return false;

        } catch (TokenExpiredException e) {
            log.error("Token expiré: " + e.getMessage());
            return true;
        }
    }
}
