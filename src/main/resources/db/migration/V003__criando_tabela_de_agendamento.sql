CREATE TABLE agendamento
(
    id            SERIAL    NOT NULL PRIMARY KEY,
    data_chegada  TIMESTAMP NOT NULL,
    data_saida    TIMESTAMP NOT NULL,
    ativo         BOOLEAN   NOT NULL,
    cliente_id    INTEGER   NOT NULL,
    consumidor_id INTEGER   NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES usuario (id),
    FOREIGN KEY (consumidor_id) REFERENCES usuario (id)
);