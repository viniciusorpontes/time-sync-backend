CREATE TABLE usuario
(
    id       SERIAL       NOT NULL PRIMARY KEY,
    cpf      VARCHAR(20)  NOT NULL,
    nome     VARCHAR(150) NOT NULL,
    email    VARCHAR(200) NOT NULL,
    telefone VARCHAR(20)  NOT NULL,
    senha    VARCHAR(30)  NOT NULL,
    tipo     VARCHAR(15)  NOT NULL,
    ativo    BOOLEAN      NOT NULL
);
