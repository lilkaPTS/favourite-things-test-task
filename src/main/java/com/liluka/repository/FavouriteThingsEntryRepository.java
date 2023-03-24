package com.liluka.repository;

import com.liluka.model.FavouriteThingsEntry;
import com.liluka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavouriteThingsEntryRepository extends JpaRepository<FavouriteThingsEntry, Long> {
    Optional<FavouriteThingsEntry> findByUser(User user);
}
