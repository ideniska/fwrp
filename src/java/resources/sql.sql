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
select * from user;

use fwrp;
INSERT INTO User (first_name, last_name, address, phone, email, password, user_type, location, food_preference, notifications, org_name, credit)
VALUES 
('Gustavo', 'Adami', '123 Maple St', '555-1234', 'adamig@algonquincollege.com', '123', 1, 'Ottawa', 'Not Vegan', 1, NULL, 100.00),
('Gordon', 'Freeman', '456 Oak St', '555-5678', 'freeman@blackmesa.com', 'crowbarheart', 2, 'Kanata', NULL, 2, 'Black Mesa', 200.00),
('JC', 'Denton', '789 Pine St', '555-8765', 'denton@machina.com', '123', 3, 'Ottawa', NULL, NULL, NULL, 300.00),
('Marty', 'McFly', '321 Cedar St', '555-2345', 'mcfly@bttf.com', '123', 1, 'Kanata', 'Vegan', 3, NULL, 150.00),
('Emmett', 'Brown', '654 Spruce St', '555-6789', 'brown@bttf.com', '123', 2, 'Ottawa', NULL, 4, 'Help Foundation', 250.00),
('Goku', 'Son', '987 Birch St', '555-9876', 'goku@dbz.com', '123', 3, 'Kanata', NULL, NULL, NULL, 350.00);

-- Insert inventory after the users.
use fwrp;
INSERT INTO Inventory (food_name, quantity, exp_date, surplus, price, user_id, food_preference, location)
VALUES 
('Oranges', 150, '2024-08-25', 0, 1.20, 3, 'Vegan', 'Ottawa'),
('Pork', 75, '2024-08-26', 1, 4.60, 3, 'Not Vegan', 'Kanata'),
('Tomatoes', 120, '2024-08-27', 0, 0.90, 3, 'Vegan', 'Ottawa'),
('Lettuce', 180, '2024-08-28', 1, 1.00, 6, 'Vegan', 'Kanata'),
('Bacon', 130, '2024-08-29', 0, 3.30, 6, 'Not Vegan', 'Ottawa'),
('Peppers', 90, '2024-08-30', 1, 1.10, 6, 'Vegan', 'Kanata');
