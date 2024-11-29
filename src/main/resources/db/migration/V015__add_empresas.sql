--- Empresa 1
INSERT INTO empresas (nome, endereco, telefone, ativo)
VALUES ('Barbearia Corte & Estilo', 'Rua das Palmeiras, 123 - Bairro Central, São Paulo', '(11) 98765-4321', true);

INSERT INTO empresas_usuarios (empresa_id, usuario_id, confirmado, gestor, ativo)
VALUES (1, 1, true, true, true);
INSERT INTO empresas_usuarios (empresa_id, usuario_id, confirmado, gestor, ativo)
VALUES (1, 2, true, true, true);

--- Empresa 2
INSERT INTO empresas (nome, endereco, telefone, ativo)
VALUES ('Barbearia Imperial Class', 'Rua do Lavradio, 456 - Centro, Rio de Janeiro - RJ', '(21) 3456-7890', true);

INSERT INTO empresas_usuarios (empresa_id, usuario_id, confirmado, gestor, ativo)
VALUES (2, 1, true, true, true);
INSERT INTO empresas_usuarios (empresa_id, usuario_id, confirmado, gestor, ativo)
VALUES (2, 3, true, false, true);

--- Empresa 3
INSERT INTO empresas (nome, endereco, telefone, ativo)
VALUES ('Consultório Médico Silva', 'Rua das Flores, 234 - Jardim Primavera, São Paulo - SP', '(11) 3333-7890', true);

INSERT INTO empresas_usuarios (empresa_id, usuario_id, confirmado, gestor, ativo)
VALUES (3, 4, true, true, true);

--- Empresa 4
INSERT INTO empresas (nome, endereco, telefone, ativo)
VALUES ('BellaForma Clínica Estética', 'Avenida Paulista, 567 - Bela Vista, São Paulo - SP', '(11) 99988-7766', true);

INSERT INTO empresas_usuarios (empresa_id, usuario_id, confirmado, gestor, ativo)
VALUES (4, 5, true, true, true);

--- Empresa 5
INSERT INTO empresas (nome, endereco, telefone, ativo)
VALUES ('Animal Care Pet Shop BH', 'Rua dos Bichos, 123 - Vila Jardim, Belo Horizonte - MG', '(31) 3221-3344', true);

INSERT INTO empresas_usuarios (empresa_id, usuario_id, confirmado, gestor, ativo)
VALUES (5, 6, true, true, true);

--- Empresa 6
INSERT INTO empresas (nome, endereco, telefone, ativo)
VALUES ('Sorriso Perfeito Odonto', 'Rua do Sol, 890 - Centro, Curitiba - PR', '(41) 3245-9876', true);

INSERT INTO empresas_usuarios (empresa_id, usuario_id, confirmado, gestor, ativo)
VALUES (6, 7, true, true, true);
