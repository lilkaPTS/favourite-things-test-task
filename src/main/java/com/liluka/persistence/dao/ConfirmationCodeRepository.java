package com.liluka.persistence.dao;

import com.liluka.persistence.model.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    Optional<ConfirmationCode> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ConfirmationCode cc SET cc.code = :code WHERE cc.email = :email")
    void updateCode(@Param("email") String email, @Param("code") String code);
}
