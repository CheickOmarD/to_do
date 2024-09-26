package com.technologia.to_do.repository;

import com.technologia.to_do.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository <Token,String> {
    Optional<Token> findByToken(String jwt);
    List<Token> findAllByUsersIdAndExpiredIsFalseOrRevokedIsFalse(Long id);
    Token findByTokenAndRevokedIsFalseOrExpiredIsFalse(String jwt);
    List<Token> findAllValidTokensByUsersId(Long id);
}
