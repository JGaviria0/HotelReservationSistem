
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
    PRIMARY KEY(hotel_id, user_id),
    FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id),
    FOREIGN KEY (user_id) REFERENCES user(document)
);

CREATE TABLE guest ( 
    guestID bigint NOT NULL,
    hotel_id bigint NOT NULL,
    principal_client_id bigint NOT NULL,
    document bigint NOT NULL,
    PRIMARY KEY(guestID),
    FOREIGN KEY (hotel_id) REFERENCES reservation(hotel_id),
    FOREIGN KEY (principal_client_id) REFERENCES reservation(user_id),
    FOREIGN KEY (document) REFERENCES user(document)
);
 
