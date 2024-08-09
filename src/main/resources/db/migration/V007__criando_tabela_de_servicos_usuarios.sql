CREATE TABLE servicos_usuarios
(
    servico_id INTEGER REFERENCES agendamentos (id),
    usuario_id INTEGER REFERENCES servicos (id),
    PRIMARY KEY (servico_id, usuario_id)
);