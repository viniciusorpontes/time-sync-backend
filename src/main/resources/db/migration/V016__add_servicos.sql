-- Serviço 1 - Empresa 1
INSERT INTO servicos (nome, tempo, valor, ativo, empresa_id)
VALUES ('Cabelo', '01:00', 40, true, 1);

INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (1, 1);
INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (1, 2);

-- Serviço 2 - Empresa 1
INSERT INTO servicos (nome, tempo, valor, ativo, empresa_id)
VALUES ('Barba', '00:30', 20, true, 1);

INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (2, 1);
INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (2, 2);

-- Serviço 3 - Empresa 1
INSERT INTO servicos (nome, tempo, valor, ativo, empresa_id)
VALUES ('Cabelo e Barba', '01:30', 50, true, 1);

INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (3, 1);
INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (3, 2);

-- Serviço 4 - Empresa 1
INSERT INTO servicos (nome, tempo, valor, ativo, empresa_id)
VALUES ('Sobrancelha', '00:15', 15, true, 1);

INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (4, 1);


-- Serviço 1 - Empresa 2
INSERT INTO servicos (nome, tempo, valor, ativo, empresa_id)
VALUES ('Cabelo', '01:00', 30, true, 2);

INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (5, 1);
INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (5, 3);

-- Serviço 2 - Empresa 2
INSERT INTO servicos (nome, tempo, valor, ativo, empresa_id)
VALUES ('Barba', '00:30', 15, true, 2);

INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (6, 1);
INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (6, 3);

-- Serviço 3 - Empresa 2
INSERT INTO servicos (nome, tempo, valor, ativo, empresa_id)
VALUES ('Cabelo e Barba', '01:30', 40, true, 2);

INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (7, 1);
INSERT INTO servicos_usuarios (servico_id, usuario_id) VALUES (7, 3);

