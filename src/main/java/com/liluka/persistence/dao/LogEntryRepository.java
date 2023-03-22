package com.liluka.persistence.dao;

import com.liluka.persistence.model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
}
