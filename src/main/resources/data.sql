
insert into items(name, price)
values('pizza quattre staguione', 12.50),
      ('pizza Hawai', 13.50),
      ('pizza mozerrella', 15.80);

insert into customers(name, lastname)
values('Lorens', 'Zhou'),
      ('Piet', 'Anderson'),
      ('Jan', 'Timmers'),
      ('Rob', 'Van den Broek');

insert into employees(name, lastname)
values('Anne', 'Linhout'),
      ('Eric', 'Merel'),
      ('Steven', 'Stratenmaker');

insert into roles(rolename)
values ('ROLE_EMPLOYEE'), ('ROLE_CUSTOMER'), ('ROLE_ADMIN');

insert into users(password, username)
values('$2a$10$TedVcBnS8JC1syu/qxhxGeOQgFzMKRcmzMD4/IOfMXzdwaf1vySpa', 'lorens'),
      ('$2a$10$9ZuivNbfu.PyuBQNeGxANuzW5gRG/QZMlvzwxxB8VGUOm5wzFd.bu', 'lorens1'),
      ('$2a$10$eNGVe5rmrxKkAMpzK8YRLut5WZ/swyGLjKaIyKmn/V4uKVqWhF6wC', 'lorens2');

insert into users_roles(roles_rolename, users_username)
values('ROLE_ADMIN', 'lorens1'),
      ('ROLE_EMPLOYEE', 'lorens2'),
      ('ROLE_CUSTOMER', 'lorens');









