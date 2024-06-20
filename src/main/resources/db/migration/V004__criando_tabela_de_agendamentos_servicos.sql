CREATE TABLE agendamentos_servicos
(
    agendamento_id INTEGER REFERENCES agendamentos (id),
    servico_id     INTEGER REFERENCES servicos (id),
    PRIMARY KEY (agendamento_id, servico_id)
);