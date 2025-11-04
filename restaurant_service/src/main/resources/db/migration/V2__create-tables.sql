CREATE TABLE restaurants (
                             id BIGSERIAL PRIMARY KEY,
                             address VARCHAR(255) NOT NULL,
                             cuisine VARCHAR(255) NOT NULL,
                             name VARCHAR(255) NOT NULL
);

CREATE TABLE dishes (
                        id BIGSERIAL PRIMARY KEY,
                        description VARCHAR(255) NOT NULL,
                        image_url VARCHAR(255) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        price INTEGER NOT NULL,
                        restaurant_id BIGINT NOT NULL
);

ALTER TABLE dishes
    ADD CONSTRAINT fkpslsa9mci7gsfhwukb3mx7s6n
        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id);