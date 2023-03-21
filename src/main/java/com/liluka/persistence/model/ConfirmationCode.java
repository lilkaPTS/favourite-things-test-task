package com.liluka.persistence.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "confirmation_codes")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmationCode {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String code;

    public ConfirmationCode(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
