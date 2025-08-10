BEGIN;

CREATE TABLE IF NOT EXISTS guest
(
    guest_id      BIGSERIAL PRIMARY KEY,
    first_name    VARCHAR(128),
    last_name     VARCHAR(128),
    birth_date    DATE,
    check_in      TIMESTAMP(6) NOT NULL,
    departure     TIMESTAMP(6) NOT NULL,
    check_out     TIMESTAMP(6) NOT NULL,
    passport_data VARCHAR(255) NOT NULL,
    guest_status  VARCHAR(255) NOT NULL,
    feedback      VARCHAR(1024),
    gender        VARCHAR(10)  NOT NULL,
    CONSTRAINT guest_gender_check CHECK (gender IN ('MAN', 'WOMAN', 'OTHER', 'REFUSED')),
    CONSTRAINT guest_status_check CHECK (guest_status IN (
                                                          'EARLY_DEPARTURE', 'CHECK_OUT', 'CHECK_IN', 'LOCK_OUT',
                                                          'OCCUPIED', 'RESERVED',
                                                          'DEPARTED', 'BOOKING', 'ARRIVAL', 'SKIPPER', 'DND', 'FIT',
                                                          'VIP'))
);

CREATE TABLE IF NOT EXISTS passport
(
    passport_id BIGSERIAL PRIMARY KEY,
    guest_id    BIGINT       NOT NULL UNIQUE REFERENCES guest (guest_id) ON DELETE CASCADE,
    address     VARCHAR(128) NOT NULL,
    credit_card VARCHAR(128) NOT NULL,
    email       VARCHAR(128) NOT NULL,
    first_name  VARCHAR(128) NOT NULL,
    last_name   VARCHAR(128) NOT NULL,
    phone       VARCHAR(128) NOT NULL,
    gender      VARCHAR(10),
    CONSTRAINT passport_gender_check CHECK (gender IN ('MAN', 'WOMAN', 'OTHER', 'REFUSED'))
);

CREATE TABLE IF NOT EXISTS room
(
    room_id     BIGSERIAL PRIMARY KEY,
    capacity    INTEGER      NOT NULL,
    room_type   VARCHAR(128) NOT NULL,
    room_status VARCHAR(128) NOT NULL,
    path        VARCHAR(255),
    guest_id    BIGINT       NULL REFERENCES guest (guest_id) ON DELETE SET NULL,
    CONSTRAINT room_room_status_check CHECK (room_status IN ('VACANT', 'RESERVED', 'MAINTENANCE')),
    CONSTRAINT room_capacity_positive CHECK (capacity > 0),
    CONSTRAINT room_room_type_check CHECK (room_type IN (
                                                         'SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD', 'QUEEN', 'KING', 'TWIN',
                                                         'HOLLYWOOD', 'STUDIO',
                                                         'CABANA', 'VILLA', 'PENTHOUSES', 'STANDARD', 'DELUXE', 'JOINT',
                                                         'CONNECTING',
                                                         'SUITE', 'APARTMENT', 'JUNIOR', 'BRIDAL', 'HONEYMOON',
                                                         'PRESIDENTIAL', 'ACCESSIBLE'))
);

CREATE INDEX IF NOT EXISTS idx_room_guest_id ON room (guest_id);

CREATE TABLE IF NOT EXISTS booking
(
    booking_id      BIGSERIAL PRIMARY KEY,
    final_bill      BIGINT,
    departure       TIMESTAMP,
    checkin_date    TIMESTAMP,
    checkout_date   TIMESTAMP,
    nights          INTEGER,
    rate            INTEGER,
    early_departure BOOLEAN DEFAULT FALSE,
    closed          BOOLEAN DEFAULT FALSE,
    guest_id        BIGINT REFERENCES guest (guest_id) ON DELETE SET NULL,
    room_id         BIGINT REFERENCES room (room_id) ON DELETE SET NULL
);

COMMIT;
