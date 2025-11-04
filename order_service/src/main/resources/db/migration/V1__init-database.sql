DO $$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'food_delivery_platform_orders') THEN
            CREATE DATABASE food_delivery_platform_orders;
        END IF;
    END
$$;