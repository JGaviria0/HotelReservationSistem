
CREATE database hotelReservations; 
use hotelReservations;

CREATE TABLE user (
    document bigint NOT NULL,
    name varchar(255) NOT NULL,
    phone bigint NOT NULL,
    document_type varchar(255) NOT NULL,
    user_type varchar(20) NOT NULL,
    password varchar(255) NOT NULL,
    PRIMARY KEY(document)
);

CREATE TABLE hotel(
    hotel_id bigint NOT NULL,
    name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    phone bigint NOT NULL,
    number_of_rooms int NOT NULL,
    capacity int NOT NULL,
    no_single int NOT NULL,
    no_double int NOT NULL,
    no_triple int NOT NULL,
    no_quad int NOT NULL,
    imagen varchar(255) NOT NULL,
    location varchar(255) NOT NULL, 
    owner_id bigint NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES user(document),
    PRIMARY KEY(hotel_id)
);

CREATE TABLE reservation (
    hotel_id bigint NOT NULL,
    user_id bigint NOT NULL,
    init_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    status varchar(20) NOT NULL,
    no_single int NOT NULL,
    no_double int NOT NULL,
    no_triple int NOT NULL,
    no_quad int NOT NULL,
    PRIMARY KEY(hotel_id, user_id),
    FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(document) ON DELETE CASCADE
);

CREATE TABLE guest ( 
    guest_id bigint NOT NULL AUTO_INCREMENT,
    hotel_id bigint NOT NULL,
    principal_client_id bigint NOT NULL,
    document bigint NOT NULL,
    PRIMARY KEY(guest_id),
    FOREIGN KEY (hotel_id) REFERENCES reservation(hotel_id),
    FOREIGN KEY (principal_client_id) REFERENCES reservation(user_id)
);
 


"SELECT" +
"    hotel_id," +
"    SUM(no_single) AS available_single," +
"    SUM(no_double) AS available_double," +
"    SUM(no_triple) AS available_triple," +
"    SUM(no_quad) AS available_quad" +
"FROM" +
"    reservation" +
"WHERE" +
"    hotel_id = ?1 AND (" +
"        (init_date <= ?2 AND ?2 < end_date) OR" +
"        (init_date <= ?2 AND ?3 < end_date) OR" +
"        (init_date <= ?3 AND ?3 < end_date) " +
"    )" +
"GROUP BY" +
"    hotel_id;" 




-- SELECT
--     h.hotel_id,
--     h.name AS hotel_name,
--     h.number_of_rooms,
--     h.no_single - COALESCE(r.no_single, 0) AS available_single,
--     h.no_double - COALESCE(r.no_double, 0) AS available_double,
--     h.no_triple - COALESCE(r.no_triple, 0) AS available_triple,
--     h.no_quad - COALESCE(r.no_quad, 0) AS available_quad
-- FROM
--     hotel h
-- LEFT JOIN (
--     SELECT
--         hotel_id,
--         SUM(no_single) AS no_single,
--         SUM(no_double) AS no_double,
--         SUM(no_triple) AS no_triple,
--         SUM(no_quad) AS no_quad
--     FROM
--         reservation
--     WHERE
--         init_date >= '2023-08-02' AND init_date <= '2023-08-05'
--     GROUP BY
--         hotel_id
-- ) r ON h.hotel_id = r.hotel_id
-- WHERE
--     h.hotel_id = 111111;
