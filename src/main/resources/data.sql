DELETE
FROM custratings;

DELETE
FROM diners;

DELETE
FROM images;

DELETE
FROM menus;

DELETE
FROM operators;

DELETE
FROM trucks;

INSERT INTO CUSTRATINGS (ratingid, rating)
VALUES (1, 4),
       (2, 5);

INSERT INTO DINERS (dinerid, username, password, currentlocation, favetrucks)
VALUES (1, 'Ramasundar', 'lambdallama', '47.7066144, -116.8551', ''),
       (2, 'Alex123', 'lambdallama', '40.7127281, -74.0060152', '');

INSERT INTO IMAGES (imgid, image)
VALUES (1, ''),
       (2, '');

INSERT INTO MENUS(menuid, itemname, itemdescription, itemprice, truck)
VALUES (1, 'taco', 'Meat, cheese, and vegetables in a tortilla', 4.35, ''),
       (2, 'cheeseburger', 'Meat, and cheese on a bun', 5.25, '');

INSERT INTO OPERATORS (userid, username, password, email, trucksowned)
VALUES (1, 'coolrunner12', 'lambdallama', 'coolrunner12@gmail.com', ''),
       (2, 'restaurantguy32', 'lambdallama', 'restaurantguy32@gmail.com', '');

INSERT INTO TRUCKS (truckid, cuisinetype, customerratings, customerratingavg,
                    departuretime, location, imageoftruck)
VALUES (1, 'American', '', 3, 1430, '47.7066144, -116.8551', ''),
       (2, 'Indian', '', 4, 1400, '40.7127281, -74.0060152', '');

alter sequence hibernate_sequence restart with 15;