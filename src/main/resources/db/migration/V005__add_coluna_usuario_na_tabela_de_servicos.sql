ALTER TABLE servicos
    ADD usuario_id INTEGER;

ALTER TABLE servicos
    ADD CONSTRAINT fk_servico_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios(id);


