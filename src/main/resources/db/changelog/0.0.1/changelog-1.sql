--liquibase formatted sql

--changeset mqqqmi:1
--comment first migration

CREATE TABLE music_groups();

CREATE TABLE labels(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    founded_year int
);

INSERT INTO labels(name, founded_year)
VALUES ( 'Sony Music Entertainment', 2004),
       ( 'Universal Music Group', 1934),
       ( 'Interscope Records', 1989),
       ( 'Columbia Records', 1887),
       ( 'Capitol Records', 1942),
       ( 'Republic Records', 1995),
       ( 'Motown Records', 1959);
