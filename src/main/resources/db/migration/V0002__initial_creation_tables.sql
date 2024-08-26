CREATE TABLE ingredients (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL
);

CREATE TABLE coffee (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE recipes (
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(255) NOT NULL
);

CREATE TABLE recipe_ingredients (
    id BIGSERIAL PRIMARY KEY,
    recipe_id BIGINT NOT NULL REFERENCES recipes(id) ON DELETE CASCADE,
    ingredient_id BIGINT NOT NULL REFERENCES ingredients(id) ON DELETE CASCADE,
    quantity INT NOT NULL
);

CREATE TABLE statistics (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    coffee_name VARCHAR(255) NOT NULL,
    order_count INT NOT NULL
);

CREATE INDEX order_count_index ON statistics (order_count);