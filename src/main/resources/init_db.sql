CREATE TABLE manufacturers
(
    id        SERIAL,
    name      VARCHAR(100),
    country   VARCHAR(100),
    is_deleted boolean DEFAULT false,
    PRIMARY KEY (id)
);

ALTER TABLE manufacturers
    OWNER to postgres;
