CREATE TABLE agendamento_servico
(
    agendamento_id INTEGER REFERENCES agendamento (id),
    servico_id     INTEGER REFERENCES servico (id),
    PRIMARY KEY (agendamento_id, servico_id)
);