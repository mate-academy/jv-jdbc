-- Table: taxi-service.manufacturers

-- DROP TABLE IF EXISTS "manufacturers ";

CREATE TABLE IF NOT EXISTS "manufacturers "
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    "name " character varying(255) COLLATE pg_catalog."default" NOT NULL,
    country character varying(255) COLLATE pg_catalog."default" NOT NULL,
    is_deleted boolean NOT NULL DEFAULT false,
    CONSTRAINT "manufacturers _pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS "manufacturers "
    OWNER to postgres;