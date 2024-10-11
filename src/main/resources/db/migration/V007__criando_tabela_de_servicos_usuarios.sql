CREATE TABLE servicos_usuarios
(
    servico_id INTEGER REFERENCES servicos (id),
    usuario_id INTEGER REFERENCES usuarios (id),
    PRIMARY KEY (servico_id, usuario_id)
);