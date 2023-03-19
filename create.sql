create sequence hibernate_sequence start 1 increment 1;
create table confirmation_codes (id int8 not null, code varchar(255) not null, email varchar(255) not null, primary key (id));
create table users (id int8 not null, dob date, email varchar(255), enabled boolean not null, name varchar(255), password varchar(255), role varchar(255), primary key (id));
