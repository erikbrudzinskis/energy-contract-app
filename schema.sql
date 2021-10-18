CREATE TABLE IF NOT EXISTS users_types
(
    id serial NOT NULL,
    name character varying(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contracts_types
(
    id serial NOT NULL,
    name character varying(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id serial NOT NULL,
    name character varying(50) NOT NULL,
    type_id integer NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(100) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT type_id FOREIGN KEY (type_id) REFERENCES users_types (id)
);

CREATE TABLE IF NOT EXISTS contracts
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    type_id integer NOT NULL,
    created_on date NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT type_id FOREIGN KEY (type_id) REFERENCES contracts_types (id)
);