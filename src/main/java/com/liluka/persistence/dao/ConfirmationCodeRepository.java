package com.liluka.persistence.dao;

import com.liluka.persistence.model.ConfirmationCode;
import com.liluka.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    Optional<ConfirmationCode> findByUser(User user);

    Optional<ConfirmationCode> findByToken(String token);

    @Transactional
    void deleteByUser(User user);
}
