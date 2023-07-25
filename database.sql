
CREATE database hotelReservations; 
use hotelReservations;

CREATE TABLE user (
    userID int NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    phone varchar(255) NOT NULL,
    document varchar(255) NOT NULL,
    document_type varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    PRIMARY KEY(userID)
);

CREATE TABLE hotel(
    hotelID int NOT NULL,
    name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    phone varchar(255) NOT NULL,
    number_of_rooms int NOT NULL,
    capacity int NOT NULL,
    no_single int NOT NULL,
    no_double int NOT NULL,
    no_triple int NOT NULL,
    no_quad int NOT NULL,
    PRIMARY KEY(hotelID)
);

CREATE TABLE reservation (
    reservationID int NOT NULL, --reservationID = hotelID + userID;
    hotelID int NOT NULL,
    init_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    document varchar(20) NOT NULL,
    principal_clientID int NOT NULL,
    status varchar(20) NOT NULL,
    PRIMARY KEY(reservationID),r45
    FOREIGN KEY (hotelID) REFERENCES hotel(hotelID),
    FOREIGN KEY (principal_clientID) REFERENCES user(userID)
);

CREATE TABLE guest ( 
    guestID int NOT NULL,
    reservationID int NOT NULL,
    userID int NOT NULL,
    PRIMARY KEY(guestID),
    FOREIGN KEY (reservationID) REFERENCES reservation(reservationID),
    FOREIGN KEY (userID) REFERENCES user(userID)
);
 
