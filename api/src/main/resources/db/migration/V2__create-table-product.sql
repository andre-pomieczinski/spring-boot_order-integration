create table products (
    id bigint not null auto_increment,
    name varchar(100) not null,
    description varchar(200) not null,
    unit_value double not null,
    weight double not null,
    active tinyint default 1 not null,
    primary key(id)
);