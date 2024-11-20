CREATE TABLE empresas_usuarios
(
    empresa_id BIGINT REFERENCES empresas (id),
    usuario_id BIGINT REFERENCES usuarios (id),
    confirmado BOOLEAN NOT NULL DEFAULT FALSE,
    gestor     BOOLEAN NOT NULL DEFAULT FALSE,
    ativo      BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (empresa_id, usuario_id)
);