CREATE TABLE usuarios
(
    id       SERIAL       NOT NULL PRIMARY KEY,
    cpf      VARCHAR(20)  NOT NULL,
    nome     VARCHAR(150) NOT NULL,
    email    VARCHAR(200) NOT NULL,
    telefone VARCHAR(20)  NOT NULL,
    senha    VARCHAR(255)  NOT NULL,
    tipo     INT          NOT NULL,
    ativo    BOOLEAN      NOT NULL
);
