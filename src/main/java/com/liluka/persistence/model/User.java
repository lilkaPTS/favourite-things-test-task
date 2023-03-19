package com.liluka.persistence.model;

import com.liluka.enums.Role;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private boolean enabled;

    public User(String email, String password, String name, Date dob, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.role = role;
        this.enabled = false;
    }
}
