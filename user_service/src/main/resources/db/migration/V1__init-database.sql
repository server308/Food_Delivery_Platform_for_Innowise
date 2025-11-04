DO $$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'food_delivery_platform_users') THEN
            CREATE DATABASE food_delivery_platform_users;
        END IF;
    END
$$;