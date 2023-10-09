create table order_items (
    id bigint not null auto_increment,
    order_id bigint not null,
    product_id bigint not null,
    quantity int not null,
    primary key(id),
    constraint fk_orders_items_order_id foreign key(order_id) references orders(id),
    constraint fk_orders_items_product_id foreign key(product_id) references products(id)
);