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
    notifications INT
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
    price DECIMAL(10, 2)
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


-- Insert data into User table
INSERT INTO User (first_name, last_name, address, phone, email, password, user_type, location, communication, food_preference, notifications)
VALUES 
('John', 'Doe','111 road','613-1111111','john.doe@example.com', 'password123', 1, 'New York', 1, 'Vegan', 1),
('Jane', 'Smith','222 cres','873-2222222' ,'jane.smith@example.com', 'securePass', 2, 'Los Angeles', 2, 'Vegetarian', 0),
('Alice','Johnson','333 street', '343-3333333','alice.johnson@example.com', 'alicePass', 1, 'Chicago', 1, 'Non-Vegetarian', 1),
('Bob', 'Brown', '444 road', '373-4444444','bob.brown@example.com', 'bobPass', 2, 'Houston', 0, 'Vegan', 1);

-- Insert data into UserTransaction table
INSERT INTO UserTransaction (user_id, transaction_date)
VALUES 
(1, '2024-07-01'),
(2, '2024-07-02'),
(1, '2024-07-03'),
(3, '2024-07-04');

-- Insert data into Inventory table
INSERT INTO Inventory (food_name, quantity, exp_date, surplus, price)
VALUES 
('Apple', 100, '2024-08-01', 1, 1),
('Banana', 150, '2024-07-20', 0, 0.5),
('Carrot', 200, '2024-09-01', 1, 0.3),
('Dates', 50, '2024-12-01', 1, 3);

-- Insert data into TransactionItem tableo76
INSERT INTO TransactionItem (usertransaction_id, food_id, quantity, price)
VALUES 
(1, 1, 10, 1),
(1, 2, 20, 0.5),
(2, 3, 15, 0.3),
(3, 4, 5, 3);

select * from Inventory
