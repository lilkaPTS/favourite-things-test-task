package com.liluka.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "confirmation_codes")
@Data
@NoArgsConstructor
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "FK_CONFIRMATION_CODE_USER"))
    private User user;
    private String token;


    public ConfirmationCode(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
