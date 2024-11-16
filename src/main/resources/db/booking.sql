CREATE TABLE IF NOT EXISTS passport (
    guest_id bigint not null unique,
    passport_id bigserial primary key,
    address varchar(128) not null,
    credit_card varchar(128) not null,
    email varchar(128) not null,
    first_name varchar(128) not null,
    last_name varchar(128) not null,
    phone varchar(128) not null,
    gender varchar(255),
    constraint passport_gender_check check (gender in ('MAN', 'WOMAN', 'OTHER', 'REFUSED'))
);


CREATE TABLE IF NOT EXISTS Booking
(
    booking_id     SERIAL PRIMARY KEY,
    finalBill      BIGINT,
    departure      TIMESTAMP,
    checkInDate    TIMESTAMP,
    checkOutDate   TIMESTAMP,
    nights         INTEGER,
    rate           INTEGER,
    earlyDeparture BOOLEAN,
    close          BOOLEAN,
    guest_id       BIGINT REFERENCES Guest (guest_id),
    room_id        BIGINT REFERENCES Room (room_id)
);

