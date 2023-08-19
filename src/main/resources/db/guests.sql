create table if not exists guest(
    id             bigserial
        primary key,
    arrival_date   timestamp(6),
    departure_date timestamp(6),
    passport_data  varchar(255),
    room_id        bigint
        references room
);

INSERT INTO guest (passport_data, arrival_date, departure_date, room_id)
VALUES ('John Doe', '2023-08-18 12:00:00', '2023-08-21 10:00:00', 1),
       ('Jane Smith', '2023-08-19 14:00:00', '2023-08-22 11:00:00', 2),
       ('Michael Johnson', '2023-08-20 15:30:00', '2023-08-23 09:30:00', 3),
       ('Alice Brown', '2023-08-21 13:45:00', '2023-08-24 08:45:00', 4),
       ('Bob Williams', '2023-08-22 11:15:00', '2023-08-25 10:15:00', 5),
       ('Eva Davis', '2023-08-23 09:30:00', '2023-08-26 12:30:00', 6),
       ('David Wilson', '2023-08-24 17:00:00', '2023-08-27 14:00:00', 7),
       ('Sophia Martin', '2023-08-25 10:30:00', '2023-08-28 11:30:00', 8),
       ('Liam Garcia', '2023-08-26 08:15:00', '2023-08-29 09:15:00', 9),
       ('Olivia Rodriguez', '2023-08-27 14:30:00', '2023-08-30 16:30:00', 10);
