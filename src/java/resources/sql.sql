DROP DATABASE IF EXISTS fwrp;

CREATE DATABASE fwrp;

USE fwrp;

CREATE TABLE User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    user_type INT,
    location VARCHAR(255),
    communication INT,
    food_preference VARCHAR(255),
    notifications INT,
    org_name VARCHAR(255),
credit DECIMAL(10, 2) 
);




CREATE TABLE UserTransaction (
    usertransaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    transaction_date DATE,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Inventory (
    food_id INT PRIMARY KEY AUTO_INCREMENT,
    food_name VARCHAR(255),
    quantity INT,
    exp_date DATE,
    surplus INT,
    price DECIMAL(10, 2),
    user_id INT,
    food_preference ENUM('Vegan', 'Not Vegan'),
    location ENUM('Ottawa', 'Kanata'),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE TransactionItem (
    transactionitem_id INT PRIMARY KEY AUTO_INCREMENT,
    usertransaction_id INT,
    food_id INT,
    quantity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (usertransaction_id) REFERENCES UserTransaction(usertransaction_id),
    FOREIGN KEY (food_id) REFERENCES Inventory(food_id)
);

select * from inventory;