DO $$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'food_delivery_platform_restaurants') THEN
            CREATE DATABASE food_delivery_platform_restaurants;
        END IF;
    END
$$;