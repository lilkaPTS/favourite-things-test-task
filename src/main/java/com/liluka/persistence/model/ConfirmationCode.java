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
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "FK_VERIFY_USER"))
    private User user;
    private String token;


    public ConfirmationCode(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
