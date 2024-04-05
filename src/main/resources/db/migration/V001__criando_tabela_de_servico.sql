CREATE TABLE servico
(
    id    SERIAL        NOT NULL PRIMARY KEY,
    nome  VARCHAR(100)  NOT NULL,
    tempo TIME          NOT NULL,
    valor DECIMAL(8, 2) NOT NULL,
    ativo BOOLEAN       NOT NULL
);
