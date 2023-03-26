package com.liluka.model;

import com.liluka.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String name;
    private LocalDate dob;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<LogEntry> logEntries;

    public User(String email, String password, String name, LocalDate dob, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.role = role;
        this.enabled = false;
    }
}
