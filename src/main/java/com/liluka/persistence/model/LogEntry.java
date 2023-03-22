package com.liluka.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "logs")
@Data
@NoArgsConstructor
public class LogEntry {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_LOG_ENTRY_USER"))
    private User user;
    private String endpoint;
    @Enumerated(value = EnumType.STRING)
    private HttpStatus httpStatus;

    public LogEntry(User user, String endpoint, HttpStatus httpStatus) {
        this.date = new Date();
        this.user = user;
        this.endpoint = endpoint;
        this.httpStatus = httpStatus;
    }
}
