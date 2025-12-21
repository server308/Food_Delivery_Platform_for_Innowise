CREATE TABLE public.order_items (
                                    id BIGINT NOT NULL PRIMARY KEY,
                                    dish_id BIGINT NOT NULL,
                                    price INTEGER NOT NULL,
                                    quantity INTEGER NOT NULL,
                                    order_id BIGINT NOT NULL
);

-- Последовательность для order_items.id
CREATE SEQUENCE public.order_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Связь для order_items.id
ALTER TABLE public.order_items ALTER COLUMN id SET DEFAULT nextval('public.order_items_id_seq');

-- Таблица orders
CREATE TABLE public.orders (
                               id BIGINT NOT NULL PRIMARY KEY,
                               order_date TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
                               restaurant_id BIGINT NOT NULL,
                               status VARCHAR(255) NOT NULL,
                               total_price INTEGER NOT NULL,
                               user_id BIGINT NOT NULL
);

-- Последовательность для orders.id
CREATE SEQUENCE public.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Связь для orders.id
ALTER TABLE public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq');

-- Таблица payments
CREATE TABLE public.payments (
                                 id BIGINT NOT NULL PRIMARY KEY,
                                 amount INTEGER NOT NULL,
                                 method VARCHAR(255) NOT NULL,
                                 status VARCHAR(255) NOT NULL,
                                 order_id BIGINT NOT NULL
);


-- Последовательность для payments.id
CREATE SEQUENCE public.payments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Связь для payments.id
ALTER TABLE public.payments ALTER COLUMN id SET DEFAULT nextval('public.payments_id_seq');

-- Ограничения внешних ключей
ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT fk_payments_order FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;