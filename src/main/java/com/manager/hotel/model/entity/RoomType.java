package com.manager.hotel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomType {
    SINGLE(50),
    DOUBLE(75),
    TRIPLE(100),
    QUAD(125),
    QUEEN(150),
    KING(175),
    TWIN(200),
    HOLLYWOOD(225),
    STUDIO(250),
    CABANA(275),
    VILLA(300),
    PENTHOUSES(325),
    STANDARD(350),
    DELUXE(375),
    JOINT(400),
    CONNECTING(425),
    SUIT(450),
    APARTMENT(475),
    JUNIOR(500),
    BRIDAL(525),
    HONEYMOON(550),
    PRESIDENTIAL(575),
    ACCESSIBLE(600);

    private final int rate;
}
