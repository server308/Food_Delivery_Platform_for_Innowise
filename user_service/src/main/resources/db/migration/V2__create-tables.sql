-- Создание таблицы users
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       email VARCHAR(255) UNIQUE,
                       full_name VARCHAR(200) NOT NULL,
                       password_hash VARCHAR(100) NOT NULL,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Создание таблицы role
CREATE TABLE role (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы address
CREATE TABLE address (
                         id BIGSERIAL PRIMARY KEY,
                         city VARCHAR(255) NOT NULL,
                         country VARCHAR(255) NOT NULL,
                         state VARCHAR(255) NOT NULL,
                         street VARCHAR(255) NOT NULL,
                         zip_code VARCHAR(255) NOT NULL,
                         user_id BIGINT NOT NULL
);

-- Создание таблицы user_role (many-to-many)
CREATE TABLE user_role (
                           user_id BIGINT NOT NULL,
                           role_id BIGINT NOT NULL,
                           PRIMARY KEY (user_id, role_id)
);

-- Создание индексов
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_address_user_id ON address(user_id);
CREATE INDEX idx_user_role_user_id ON user_role(user_id);
CREATE INDEX idx_user_role_role_id ON user_role(role_id);

-- Добавление внешних ключей
ALTER TABLE address
    ADD CONSTRAINT fk_address_user
        FOREIGN KEY (user_id) REFERENCES users(id)
            ON DELETE CASCADE;

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_user
        FOREIGN KEY (user_id) REFERENCES users(id)
            ON DELETE CASCADE;

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_role
        FOREIGN KEY (role_id) REFERENCES role(id)
            ON DELETE CASCADE;

-- Вставка ролей
INSERT INTO role (name) VALUES
                            ('ROLE_ADMIN'),
                            ('ROLE_USER')
ON CONFLICT (name) DO NOTHING;

-- Вставка администратора (пароль: 12345)
INSERT INTO users (email, full_name, password_hash, created_at, updated_at)
VALUES (
           'serfvaler456@gmail.com',
           'Administrator',
           '$2a$10$PXCtajnnyoiTCLjzmM/dX.a5E9y6q42p1xSRQXDLnifFDw.PaN7Fu', -- закодированный пароль 12345
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       )
ON CONFLICT (email) DO NOTHING;

-- Связывание администратора с ролью ADMIN
INSERT INTO user_role (user_id, role_id)
SELECT
    u.id,
    r.id
FROM
    users u,
    role r
WHERE
    u.email = 'serfvaler456@gmail.com'
  AND r.name = 'ROLE_ADMIN'
ON CONFLICT (user_id, role_id) DO NOTHING;