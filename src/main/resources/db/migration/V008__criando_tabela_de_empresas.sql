CREATE TABLE empresas
(
    id    SERIAL        NOT NULL PRIMARY KEY,
    nome  VARCHAR(100)  NOT NULL,
    ativo BOOLEAN       NOT NULL
);
