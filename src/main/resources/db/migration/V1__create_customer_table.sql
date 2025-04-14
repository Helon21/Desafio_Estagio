CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE customer (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(100) NOT NULL,
    birthDate DATE,
    cpf VARCHAR(11)
)