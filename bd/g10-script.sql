create database flyon;
use flyon;

-- Tabela de Agência
create table agencia (
    idAgencia int primary key auto_increment,
    cnpj char(14) unique not null,
    nome_fantasia varchar(100) unique not null,
    razao_social varchar(100) unique not null,
    representante_legal varchar(100) not null
);

-- Tabela de Endereço com relação 1:1 com Agência
create table endereco (
    id int auto_increment,
    fk_agencia int,
    cep char(8) not null,
    numero int not null,
    logradouro varchar(100) not null,
    bairro varchar(100) not null,
    cidade varchar(100) not null,
    estado char(2) not null,
    constraint pk_endereco_agencia primary key(id, fk_agencia),
    constraint fk_endereco_agencia foreign key (fk_agencia) references agencia(idAgencia) on delete cascade
);

-- Tabela de Usuário com relação 1:N com Agência
create table usuario (
    idUsuario int primary key auto_increment,
    fk_agencia int, 
    nome varchar(100) not null,
    cargo varchar(100) not null,
    email varchar(100) unique not null,
    senha varchar(20) not null,
    constraint fk_usuario_agencia foreign key (fk_agencia) references agencia(idAgencia) on delete cascade
);

create table perfil_cliente (
	id int auto_increment,
    fk_agencia int,
	perfil varchar(45) not null,
	mais_barato int default(0),
	menos_cancelamentos int default(0),
	menos_atrasos int default(0),
    constraint pk_perfil_agencia primary key(id, fk_agencia),
    constraint fk_perfil_agencia foreign key (fk_agencia) references agencia(idAgencia) on delete cascade,
	constraint chk_mais_barato check (mais_barato in (0, 1)),
	constraint chk_menos_cancelamentos check (menos_cancelamentos in (0, 1)),
	constraint chk_menos_atrasos check (menos_atrasos in (0, 1))
);

create table notificacoes (
	id int auto_increment,
    fk_agencia int,
    data_hora datetime not null,
    mensagem varchar(255) not null,
    constraint pk_notificacao_agencia primary key(id, fk_agencia),
    constraint fk_notificacao_agencia foreign key (fk_agencia) references agencia(idAgencia) on delete cascade
);

create table historico_passagens (
	id int primary key auto_increment,
	data_hora_partida_prevista datetime,
	data_hora_partida_real datetime,
	data_hora_chegada_prevista datetime,
	data_hora_chegada_real datetime,
	sigla_empresa_aerea char(3) not null,
	empresa_aerea varchar(100) not null,
	origem varchar(500) not null,
	destino varchar(500) not null,
	situacao_voo varchar(14),
	situacao_partida varchar(14),
	situacao_chegada varchar(14),
	assentos_comercializados int
);

create table sugestao (
	id int auto_increment,
    fk_agencia int,
    fk_passagem int,
    descricao varchar(100),
    constraint pk_sugestao_passagem_agencia primary key(id, fk_agencia, fk_passagem),
    constraint fk_sugestao_agencia foreign key (fk_agencia) references agencia(idAgencia) on delete cascade,
    constraint fk_sugestao_passagem foreign key (fk_passagem) references historico_passagens(id) on delete cascade
);

create table registro (
	id int primary key auto_increment,
	data_hora datetime not null,
	classificacao char(7) not null,
	mensagem varchar(255) not null,
	constraint chk_classificacao check (classificacao in ('TRACE', 'WARN', 'INFO', 'SUCCESS'))
);

-- select * from agencia;
-- select * from endereco where fk_agencia = 1;
-- select * from usuario;
