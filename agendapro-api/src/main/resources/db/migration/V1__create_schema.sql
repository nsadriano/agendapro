CREATE EXTENSION IF NOT EXISTS btree_gist;
CREATE TABLE estabelecimento (
 id UUID PRIMARY KEY, nome VARCHAR(150) NOT NULL, telefone VARCHAR(30), email VARCHAR(254),
 timezone VARCHAR(64) NOT NULL DEFAULT 'America/Sao_Paulo', ativo BOOLEAN NOT NULL DEFAULT TRUE,
 criado_em TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE usuario (
 id UUID PRIMARY KEY, nome VARCHAR(150) NOT NULL, email VARCHAR(254) NOT NULL,
 senha_hash VARCHAR(255) NOT NULL, ativo BOOLEAN NOT NULL DEFAULT TRUE,
 criado_em TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX uk_usuario_email_lower ON usuario (lower(email));

CREATE TABLE membro_estabelecimento (
 id UUID PRIMARY KEY, estabelecimento_id UUID NOT NULL REFERENCES estabelecimento(id),
 usuario_id UUID NOT NULL REFERENCES usuario(id), papel VARCHAR(30) NOT NULL,
 atende_clientes BOOLEAN NOT NULL DEFAULT TRUE, ativo BOOLEAN NOT NULL DEFAULT TRUE,
 CONSTRAINT uk_membro_usuario_est UNIQUE (estabelecimento_id, usuario_id),
 CONSTRAINT ck_membro_papel CHECK (papel IN ('ADMIN','PROFISSIONAL')),
 CONSTRAINT uk_membro_tenant UNIQUE (estabelecimento_id,id)
);

CREATE TABLE cliente (
 id UUID PRIMARY KEY, estabelecimento_id UUID NOT NULL REFERENCES estabelecimento(id),
 nome VARCHAR(150) NOT NULL, telefone VARCHAR(30), email VARCHAR(254), observacoes TEXT,
 ativo BOOLEAN NOT NULL DEFAULT TRUE, criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
 CONSTRAINT uk_cliente_tenant UNIQUE (estabelecimento_id,id)
);

CREATE TABLE servico (
 id UUID PRIMARY KEY, estabelecimento_id UUID NOT NULL REFERENCES estabelecimento(id),
 nome VARCHAR(150) NOT NULL, descricao TEXT, preco NUMERIC(12,2) NOT NULL,
 duracao_minutos INTEGER NOT NULL, ativo BOOLEAN NOT NULL DEFAULT TRUE,
 CONSTRAINT ck_servico_preco CHECK (preco >= 0),
 CONSTRAINT ck_servico_duracao CHECK (duracao_minutos > 0),
 CONSTRAINT uk_servico_tenant UNIQUE (estabelecimento_id,id)
);

CREATE TABLE horario_funcionamento (
 id UUID PRIMARY KEY, estabelecimento_id UUID NOT NULL REFERENCES estabelecimento(id),
 dia_semana INTEGER NOT NULL, hora_inicio TIME NOT NULL, hora_fim TIME NOT NULL,
 ativo BOOLEAN NOT NULL DEFAULT TRUE,
 CONSTRAINT ck_horario_dia CHECK (dia_semana BETWEEN 1 AND 7),
 CONSTRAINT ck_horario_intervalo CHECK (hora_inicio < hora_fim)
);

CREATE TABLE bloqueio_agenda (
 id UUID PRIMARY KEY, estabelecimento_id UUID NOT NULL REFERENCES estabelecimento(id),
 profissional_id UUID NOT NULL,
 inicio TIMESTAMPTZ NOT NULL, fim TIMESTAMPTZ NOT NULL, motivo TEXT,
 CONSTRAINT fk_bloqueio_prof_tenant FOREIGN KEY(estabelecimento_id,profissional_id)
 REFERENCES membro_estabelecimento(estabelecimento_id,id),
 CONSTRAINT ck_bloqueio_intervalo CHECK (inicio < fim)
);

CREATE TABLE agendamento (
 id UUID PRIMARY KEY, estabelecimento_id UUID NOT NULL REFERENCES estabelecimento(id),
 cliente_id UUID NOT NULL, profissional_id UUID NOT NULL, servico_id UUID NOT NULL,
 inicio TIMESTAMPTZ NOT NULL, fim TIMESTAMPTZ NOT NULL,
 status VARCHAR(30) NOT NULL DEFAULT 'AGENDADO',
 preco_previsto NUMERIC(12,2) NOT NULL, valor_cobrado NUMERIC(12,2),
 observacoes TEXT, criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
 CONSTRAINT fk_ag_cliente_tenant FOREIGN KEY(estabelecimento_id,cliente_id) REFERENCES cliente(estabelecimento_id,id),
 CONSTRAINT fk_ag_prof_tenant FOREIGN KEY(estabelecimento_id,profissional_id) REFERENCES membro_estabelecimento(estabelecimento_id,id),
 CONSTRAINT fk_ag_servico_tenant FOREIGN KEY(estabelecimento_id,servico_id) REFERENCES servico(estabelecimento_id,id),
 CONSTRAINT ck_ag_intervalo CHECK (inicio < fim),
 CONSTRAINT ck_ag_valores CHECK (preco_previsto >= 0 AND (valor_cobrado IS NULL OR valor_cobrado >= 0)),
 CONSTRAINT ck_ag_status CHECK (status IN ('AGENDADO','CONFIRMADO','CONCLUIDO','CANCELADO','NAO_COMPARECEU')),
 CONSTRAINT ex_ag_sem_sobreposicao EXCLUDE USING gist
 (estabelecimento_id WITH =, profissional_id WITH =, tstzrange(inicio, fim, '[)') WITH &&)
 WHERE (status IN ('AGENDADO','CONFIRMADO'))
);

CREATE INDEX idx_cliente_est_nome ON cliente(estabelecimento_id,nome);
CREATE INDEX idx_servico_est_ativo ON servico(estabelecimento_id,ativo);
CREATE INDEX idx_ag_est_inicio ON agendamento(estabelecimento_id,inicio);
CREATE INDEX idx_ag_cliente ON agendamento(estabelecimento_id,cliente_id);
CREATE INDEX idx_bloqueio_prof_inicio ON bloqueio_agenda(estabelecimento_id,profissional_id,inicio);
CREATE INDEX idx_membro_usuario ON membro_estabelecimento(usuario_id);
