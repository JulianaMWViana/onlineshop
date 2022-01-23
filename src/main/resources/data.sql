INSERT INTO End_User(name, password, email, phone, address) VALUES('Juliana', '$2a$10$t7UE1oOtrRFd7kVXpRUj9.Sr3.A0IBDEykRDcBjFlvowM87KHzQBq', 'juliana@cct.com', '0831234322', '1 OConnell Street');
INSERT INTO End_User(name, password, email, phone, address) VALUES('Customer1', '$2a$10$t7UE1oOtrRFd7kVXpRUj9.Sr3.A0IBDEykRDcBjFlvowM87KHzQBq', 'customer1@cct.com', '0831234321', '2 Merrion Road');

INSERT INTO ROLES(role_Name) VALUES('admin');
INSERT INTO ROLES(role_Name) VALUES('customer');

INSERT INTO END_USER_ROLES(End_Users_id, Roles_Id) VALUES(1, 1);
INSERT INTO END_USER_ROLES(End_Users_id, Roles_Id) VALUES(2, 2);


INSERT INTO FILES(FILE_NAME, FILE_TYPE, URL) VALUES ('4d5211cc-f750-490c-b500-0dab3bdbe1dc.jpg', 'jpg', 'https://onlineshoppingvegetables.s3.eu-west-1.amazonaws.com/4d5211cc-f750-490c-b500-0dab3bdbe1dc.jpg');
INSERT INTO FILES(FILE_NAME, FILE_TYPE, URL) VALUES ('75443de6-e5c0-4997-808c-ac6d9b3d1748.jpg', 'jpg', 'https://onlineshoppingvegetables.s3.eu-west-1.amazonaws.com/75443de6-e5c0-4997-808c-ac6d9b3d1748.jpg');
INSERT INTO FILES(FILE_NAME, FILE_TYPE, URL) VALUES ('530b990e-9890-4408-bd66-2398fe5f85b8.jpg', 'jpg', 'https://onlineshoppingvegetables.s3.eu-west-1.amazonaws.com/530b990e-9890-4408-bd66-2398fe5f85b8.jpg');


INSERT INTO Product(name, description, price, quantity, url) VALUES('Eggplant', 'Eggplant is very common in southern European countries and it is used in many traditional recipes', 1.99, 20, 'https://onlineshoppingvegetables.s3.eu-west-1.amazonaws.com/4d5211cc-f750-490c-b500-0dab3bdbe1dc.jpg');
INSERT INTO Product(name, description, price, quantity, url) VALUES('Broccoli', 'Broccoli is popular and widely eaten. It has a distinctive ‘mustardy’ taste and well known health benefits', 0.99, 22, 'https://onlineshoppingvegetables.s3.eu-west-1.amazonaws.com/75443de6-e5c0-4997-808c-ac6d9b3d1748.jpg');
INSERT INTO Product(name, description, price, quantity, url) VALUES('Carrot', 'Earliest records show carrots were purple; later records show red, yellow and white carrots were grown. Orange is the main type found in New Zealand', 1.09, 19, 'https://onlineshoppingvegetables.s3.eu-west-1.amazonaws.com/530b990e-9890-4408-bd66-2398fe5f85b8.jpg');

INSERT INTO Scheduling(DATE_AND_TIME) VALUES(parsedatetime('07-01-2022 13:00:00', 'dd-MM-yyyy hh:mm:ss'));

INSERT INTO Purchase(total_Price, status, End_User_Id, Scheduling_Id) VALUES(12.99, 'PROCESSING', 2, 1);

INSERT INTO Requested_Product(quantity, Product_Id, Purchase_Id) VALUES(3, 1, 1);--3 eggplants
INSERT INTO Requested_Product(quantity, Product_Id, Purchase_Id) VALUES(1, 3, 1);--1 carrot

