package com.liluka.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "favourite_things")
@Data
@NoArgsConstructor
public class FavouriteThingsEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "FK_FAVOURITE_THINGS_USER"))
    private User user;
    private String dish;
    private String color;
    private String song;
    private Integer number;
    private String date;

    public FavouriteThingsEntry(User user, String dish, String color, String song, Integer number, String date) {
        this.user = user;
        this.dish = dish;
        this.color = color;
        this.song = song;
        this.number = number;
        this.date = date;
    }
}
