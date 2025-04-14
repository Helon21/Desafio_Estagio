CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE address (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    street VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    customer_id UUID,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);
