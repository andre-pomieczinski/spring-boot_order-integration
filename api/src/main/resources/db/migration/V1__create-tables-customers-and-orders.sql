create table customers (
    id bigint not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null unique,
    cpf varchar(11) not null unique,
    phone varchar (20) not null,
    active tinyint default 1 not null,
    neighborhood varchar(100) not null,
    street varchar(100) not null,
    zipcode varchar(9) not null,
    complement varchar(100),
    number varchar(20),
    uf char(2) not null,
    city varchar(100) not null,
    primary key(id)
);
create table orders (
    id bigint not null auto_increment,
    status varchar(100) not null,
    create_date datetime not null,
    customer_id bigint not null,
    primary key(id),
    constraint fk_orders_customer_id foreign key(customer_id) references customers(id)
);