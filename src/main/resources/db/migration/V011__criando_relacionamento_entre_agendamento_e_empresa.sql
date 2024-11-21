ALTER TABLE agendamentos
    ADD COLUMN empresa_id INT,
    ADD CONSTRAINT agendamentos_empresa_id_fkey
        FOREIGN KEY (empresa_id) REFERENCES empresas (id);