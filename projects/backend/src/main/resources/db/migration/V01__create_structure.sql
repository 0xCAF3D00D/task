CREATE TABLE IF NOT EXISTS products
(
    id          uuid                     NOT NULL PRIMARY KEY,
    name        text                     NOT NULL,
    description text                     NOT NULL,
    price       numeric(21, 2)           NOT NULL,
    currency    char(3)                  NOT NULL,
    created_at  timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS purchases
(
    id           uuid                     NOT NULL PRIMARY KEY,
    product_id   uuid                     NOT NULL REFERENCES products (id),
    email        text                     NOT NULL,
    amount       numeric(21, 2)           NOT NULL,
    currency     char(3)                  NOT NULL,
    status       varchar(256)             NOT NULL,
    external_id  varchar(256),
    external_url text,
    code         bigint,

    created_at   timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_external_id ON purchases (external_id);

CREATE TABLE IF NOT EXISTS webhooks
(
    id         uuid                     NOT NULL PRIMARY KEY,
    payload    jsonb                    NOT NULL,
    status     varchar(256)             NOT NULL,
    created_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP
);
