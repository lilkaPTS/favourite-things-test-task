package com.liluka.repository;

import com.liluka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь %s не найден ", email)));
    }

    @Query("SELECT u.name FROM User u")
    List<String> findAllUserNames();
}
