drop table if exists subscriptions;
drop table if exists reviews;
drop table if exists periodicals;
drop table if exists users;
drop table if exists categories;
drop table if exists roles;
drop table if exists user_profiles;

create table roles (
	id int primary key,
	name varchar(10) unique
);


create table user_profiles(
	id int primary key auto_increment,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	city varchar(20) not null,
	address varchar(50) not null,
	zip_code varchar(10) not null
);

create table users (
	id int primary key auto_increment,
	user_name varchar(20) not null unique,
	password varchar(20) not null,
	balance decimal(6,2) unsigned default 0 not null,
	banned bool default false not null,
	lang varchar(15),
	email varchar(40) not null,
	role_id int not null,
	user_profile_id int unique,
	foreign key (role_id)
	references roles(id)
	on update restrict
	on delete restrict,
	foreign key (user_profile_id)
	references user_profiles(id)
	on update cascade
	on delete set null
);


create table categories(
	id int primary key auto_increment,
	name varchar(20) not null unique
);

create table periodicals (
	id int primary key auto_increment,
	name varchar(30) not null,
	description text(600),
	price decimal(6,2) unsigned not null,
	periodicity int unsigned not null,
	images int unsigned not null default 0,
	rating decimal(10,4) not null default 0,
	summary_rating int not null default 0,
	category varchar(20) not null,
	CONSTRAINT chk_rating CHECK (rating >= 0 AND rating <= 5)
);


create table subscriptions (
	id int primary key auto_increment,
	user_id int not null,
	periodical_id int not null,
	start_date date not null,
	end_date date not null,
	foreign key (user_id)
	references users(id)
	on update cascade
	on delete cascade,
	foreign key (periodical_id)
	references periodicals(id)
	on update cascade
	on delete cascade
);

create table reviews(
	id int primary key auto_increment,
	score int not null,
	message text(300),
	creation_date date not null,
	user_id int not null,
	periodical_id int not null,
	foreign key (user_id)
	references users(id)
	on update cascade
	on delete cascade,
	foreign key (periodical_id)
	references periodicals(id)
	on update cascade
	on delete cascade,
	CONSTRAINT chk_score CHECK (score >= 0 AND score <= 5)
);
