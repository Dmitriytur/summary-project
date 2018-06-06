insert into roles values (0, 'admin');
insert into roles values (1, 'client');

insert into users values (default, 'admin', 'admin', 
0, false, null, 'admin.test@gmail.com', 0, null);

insert into user_profiles values (1, 'Иван', 'Иванов', 'Харьков',
'ул. Пушкинская 12', '62304');

insert into users values (default, 'ivanov', 'qweqwe12',
0, false, null, 'ivanov.test@gmail.com', 1, 1);

insert into user_profiles values (2, 'Алексей', 'Петров', 'Харьков',
'Проспект Науки 56', '62490');

insert into users values (default, 'alexander', 'qweqwe12',
0, false, null,'alex.test@gmail.com', 1, 2);

insert into categories values (default, 'Business & Finance');
insert into categories values (default, 'Auto & Cycles');
insert into categories values (default, 'Music');
insert into categories values (default, 'Sports & Recreation');
insert into categories values (default, 'News & Politics');
insert into categories values (default, 'Art & Photography');
insert into categories values (default, 'Animals & Pets');







