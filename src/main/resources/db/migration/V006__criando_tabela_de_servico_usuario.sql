CREATE TABLE servico_usuario
(
    servico_id INTEGER REFERENCES servico (id),
    usuario_id INTEGER REFERENCES usuario (id),
    PRIMARY KEY (servico_id, usuario_id)
);