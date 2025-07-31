DELETE FROM user_role;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;


INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, description, calories)
VALUES ((SELECT id from users where name = 'User'), 'Ужин', 500 ),
       ((SELECT id from users where name = 'Admin'), 'Обед', 450),
       ((SELECT id from users where name = 'Guest'),'Завтрак', 300);