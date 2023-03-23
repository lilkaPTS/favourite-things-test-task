create sequence hibernate_sequence start 1 increment 1;
create table confirmation_codes (id int8 not null, token varchar(255), user_id int8 not null, primary key (id));
create table logs (id int8 not null, date timestamp, endpoint varchar(255), http_status varchar(255), user_id int8, primary key (id));
create table users (id int8 not null, dob date, email varchar(255), enabled boolean not null, name varchar(255), password varchar(255), role varchar(255), primary key (id));
alter table if exists confirmation_codes add constraint FK_VERIFY_USER foreign key (user_id) references users;
alter table if exists logs add constraint FKgqy8beil5y4almtq1tiyofije foreign key (user_id) references users;
