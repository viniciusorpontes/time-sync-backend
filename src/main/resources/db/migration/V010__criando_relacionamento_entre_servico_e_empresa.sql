ALTER TABLE servicos ADD COLUMN empresa_id INT,
ADD CONSTRAINT servicos_empresa_id_fkey FOREIGN KEY (empresa_id) REFERENCES empresas (id)