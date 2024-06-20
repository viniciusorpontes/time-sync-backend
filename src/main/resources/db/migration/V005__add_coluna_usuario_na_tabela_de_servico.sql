ALTER TABLE servico
    ADD usuario_id INTEGER;

ALTER TABLE servico
    ADD CONSTRAINT fk_servico_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id);


