create sequence table_name_id_seq
    as integer;

alter sequence table_name_id_seq owner to postgres;

create table public.customers
(
    id      integer default nextval('table_name_id_seq'::regclass) not null
        constraint table_name_pk
            primary key,
    name    varchar(100)                                           not null,
    surname varchar(100)                                           not null
);

alter table customers
    owner to postgres;

alter sequence table_name_id_seq owned by customers.id;

create table products
(
    id    serial
        constraint products_pk
            primary key,
    name  varchar(100) not null,
    price integer      not null
);

alter table products
    owner to postgres;

create unique index products_name_uindex
    on products (name);

create table purchases
(
    id          serial
        constraint purchases_pk
            primary key,
    product_id  integer
        constraint purchases_products_id_fk
            references products,
    customer_id integer not null
        constraint purchases_customers_id_fk
            references customers,
    date        date    not null
);

alter table purchases
    owner to postgres;

INSERT INTO public.customers (id, name, surname) VALUES (1, 'Sergey', 'Myasoedov');
INSERT INTO public.customers (id, name, surname) VALUES (2, 'Maria', 'Makarova');
INSERT INTO public.customers (id, name, surname) VALUES (3, 'Lionel', 'Messi');
INSERT INTO public.customers (id, name, surname) VALUES (4, 'Cristiano', 'Ronaldo');
INSERT INTO public.customers (id, name, surname) VALUES (5, 'Mike', 'Cristensen');
INSERT INTO public.customers (id, name, surname) VALUES (6, 'Иван', 'Иванович');

INSERT INTO public.products (id, name, price) VALUES (1, 'PC', 100);
INSERT INTO public.products (id, name, price) VALUES (2, 'Phone', 50);
INSERT INTO public.products (id, name, price) VALUES (3, 'Volleyball Ball', 5000);
INSERT INTO public.products (id, name, price) VALUES (4, 'Headphones', 3000);
INSERT INTO public.products (id, name, price) VALUES (5, 'Car', 100000);

INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (1, 1, 1, '2022-08-15');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (6, 2, 1, '2022-08-13');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (7, 1, 1, '2022-08-16');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (8, 1, 2, '2022-08-20');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (9, 1, 2, '2022-08-18');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (10, 2, 2, '2022-08-16');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (11, 3, 4, '2022-08-16');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (12, 3, 4, '2022-08-16');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (13, 4, 5, '2022-08-18');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (14, 5, 3, '2022-08-18');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (16, 1, 6, '2022-08-20');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (17, 1, 1, '2022-08-16');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (18, 3, 1, '2022-08-18');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (19, 4, 1, '2022-08-25');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (21, 2, 1, '2022-08-30');
INSERT INTO public.purchases (id, product_id, customer_id, date) VALUES (20, 4, 1, '2022-08-26');