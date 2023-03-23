package com.liluka.repository;

import com.liluka.model.ConfirmationCode;
import com.liluka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    Optional<ConfirmationCode> findByUser(User user);

    Optional<ConfirmationCode> findByToken(String token);

    void deleteByUser(User user);
}
