create table if not exists room
(
    room_id     SERIAL PRIMARY KEY,
    capacity    INT          NOT NULL,
    room_type   VARCHAR(128) NOT NULL,
    room_status VARCHAR(128) NOT NULL,
    path        VARCHAR(255),
    guest_id    BIGINT,
    FOREIGN KEY (guest_id) REFERENCES guest (guest_id),
    constraint room_room_status_check check (room_status in ('VACANT', 'RESERVED', 'RESERVED', 'MAINTENANCE')),
    constraint room_room_type_check check (room_type in
                                           ('SINGLE', 'DOUBLE', 'TRIPLE',
                                            'QUAD', 'QUEEN', 'KING', 'TWIN',
                                            'HOLLYWOOD', 'STUDIO', 'CABANA',
                                            'VILLA', 'PENTHOUSES', 'STANDARD',
                                            'DELUXE', 'JOINT', 'CONNECTING',
                                            'SUIT', 'APARTMENT', 'JUNIOR',
                                            'BRIDAL', 'HONEYMOON',
                                            'PRESIDENTIAL', 'ACCESSIBLE'))
);

INSERT INTO room (capacity, room_type, room_status, path, guest_id)
VALUES (1, 'SINGLE', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_1.jpg', null),
       (2, 'DOUBLE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_2.jpg', null),
       (3, 'TRIPLE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_3.jpg', null),
       (4, 'QUAD', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_4.jpg', null),
       (2, 'QUEEN', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_5.jpg', null),
       (2, 'KING', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_6.jpg', null),
       (2, 'TWIN', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_7.jpg', null),
       (2, 'HOLLYWOOD', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_8.jpg', null),
       (2, 'STUDIO', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_9.jpg', null),
       (2, 'CABANA', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_10.jpg', null),
       (3, 'VILLA', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_11.jpg', null),
       (2, 'PENTHOUSES', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_12.jpg', null),
       (2, 'STANDARD', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_13.jpg', null),
       (2, 'DELUXE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_14.jpg', null),
       (4, 'JOINT', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_15.jpg', null),
       (4, 'CONNECTING', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_16.jpg', null),
       (2, 'SUIT', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_17.jpg', null),
       (2, 'APARTMENT', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_18.jpg', null),
       (2, 'JUNIOR', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_19.jpg', null),
       (2, 'BRIDAL', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_20.jpg', null),
       (2, 'HONEYMOON', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_21.jpg', null),
       (2, 'PRESIDENTIAL', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_22.jpg', null),
       (2, 'ACCESSIBLE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_23.jpg', null),
       (1, 'SINGLE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_24.jpg', null),
       (2, 'DOUBLE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_25.jpg', null),
       (3, 'TRIPLE', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_26.jpg', null),
       (1, 'SINGLE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_27.jpg', null),
       (2, 'DOUBLE', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_28.jpg', null),
       (3, 'TRIPLE', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_29.jpg', null),
       (4, 'QUAD', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_30.jpg', null),
       (2, 'QUEEN', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_31.jpg', null),
       (2, 'KING', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_32.jpg', null),
       (2, 'TWIN', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_33.jpg', null),
       (2, 'HOLLYWOOD', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_34.jpg', null),
       (2, 'STUDIO', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_35.jpg', null),
       (2, 'CABANA', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_36.jpg', null),
       (2, 'SUIT', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_37.jpg', null),
       (2, 'APARTMENT', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_38.jpg', null),
       (2, 'JUNIOR', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_39.jpg', null),
       (2, 'BRIDAL', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_40.jpg', null),
       (4, 'VILLA', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_41.jpg', null),
       (2, 'PENTHOUSES', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_42.jpg', null),
       (2, 'STANDARD', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_43.jpg', null),
       (2, 'DELUXE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_44.jpg', null),
       (4, 'JOINT', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_45.jpg', null),
       (4, 'CONNECTING', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_46.jpg', null),
       (2, 'SUIT', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_47.jpg', null),
       (2, 'APARTMENT', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_48.jpg', null),
       (2, 'JUNIOR', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_49.jpg', null),
       (2, 'BRIDAL', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_50.jpg', null),
       (1, 'SINGLE', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_101.jpg', null),
       (2, 'DOUBLE', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_102.jpg', null),
       (3, 'TRIPLE', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_103.jpg', null),
       (4, 'QUAD', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_104.jpg', null),
       (2, 'QUEEN', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_105.jpg', null),
       (2, 'KING', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_106.jpg', null),
       (2, 'TWIN', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_107.jpg', null),
       (2, 'HOLLYWOOD', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_108.jpg', null),
       (2, 'STUDIO', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_109.jpg', null),
       (2, 'CABANA', 'RESERVED', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_110.jpg', null),
       (4, 'VILLA', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_111.jpg', null),
       (2, 'QUEEN', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_112.jpg', null),
       (2, 'STANDARD', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_113.jpg', null),
       (2, 'DELUXE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_114.jpg', null),
       (4, 'JOINT', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_115.jpg', null),
       (4, 'CONNECTING', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_116.jpg', null),
       (2, 'SUIT', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_117.jpg', null),
       (2, 'APARTMENT', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_118.jpg', null),
       (2, 'JUNIOR', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_119.jpg', null),
       (2, 'BRIDAL', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_120.jpg', null),
       (2, 'HONEYMOON', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_121.jpg', null),
       (2, 'PRESIDENTIAL', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_122.jpg', null),
       (2, 'ACCESSIBLE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_123.jpg', null),
       (1, 'SINGLE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_124.jpg', null),
       (2, 'DOUBLE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_125.jpg', null),
       (3, 'TRIPLE', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_126.jpg', null),
       (1, 'SINGLE', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_127.jpg', null),
       (2, 'DOUBLE', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_128.jpg', null),
       (3, 'TRIPLE', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_129.jpg', null),
       (4, 'QUAD', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_130.jpg', null),
       (2, 'QUEEN', 'VACANT', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_131.jpg', null),
       (2, 'KING', 'MAINTENANCE', 'https://raw.githubusercontent.com/Lokankara/Images/master/rooms/image_132.jpg', null);

