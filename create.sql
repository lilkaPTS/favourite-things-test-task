create sequence hibernate_sequence start 1 increment 1;
create table confirmation_codes (id int8 not null, token varchar(255), user_id int8 not null, primary key (id));
create table logs (id int8 not null, date date, endpoint varchar(255), http_status varchar(255), user_id int8, primary key (id));
create table users (id int8 not null, dob date, email varchar(255), enabled boolean not null, name varchar(255), password varchar(255), role varchar(255), primary key (id));
create table users_log_entries (user_id int8 not null, log_entries_id int8 not null);
alter table if exists users_log_entries add constraint UK_h8d68fy20uyol4t6qq2kgw19q unique (log_entries_id);
alter table if exists confirmation_codes add constraint FK_VERIFY_USER foreign key (user_id) references users;
alter table if exists logs add constraint FK_LOG_ENTRY_USER foreign key (user_id) references users;
alter table if exists users_log_entries add constraint FKrwd2t76xoe4sftntyvqrtkq10 foreign key (log_entries_id) references logs;
alter table if exists users_log_entries add constraint FKodmnrlxm6vewrwxpalhegojqn foreign key (user_id) references users;
