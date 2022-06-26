CREATE DATABASE taxi_service_db
    WITH
    ENCODING = 'UTF8'
    LC_COLLATE = 'Ukrainian_Ukraine.1251'
    LC_CTYPE = 'Ukrainian_Ukraine.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE IF NOT EXISTS public.manufacturers
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(45) COLLATE pg_catalog."default" NOT NULL,
    country character varying(45) COLLATE pg_catalog."default" NOT NULL,
    is_deleted boolean NOT NULL DEFAULT false,
    CONSTRAINT manufacturers_pkey PRIMARY KEY (id)
)