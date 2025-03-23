create database flyon;

use flyon;

create table agencia (
id int primary key auto_increment,
codigo int not null,
cnpj char(14) unique not null,
nome_fantasia varchar(100) unique not null,
razao_social varchar(100) unique not null,
representante_legal varchar(100) not null
);

create table endereco (
id int auto_increment,
fk_agencia int,
cep char(8) not null,
numero int not null,
logradouro varchar(100) not null,
bairro varchar(100) not null,
cidade varchar(100) not null,
estado char(2) not null,
constraint pk_composta_agencia_endereco primary key (id, fk_agencia)
);

create table usuario (
id int auto_increment,
fk_agencia int,
nome varchar(100) not null,
cargo varchar(100) not null,
email varchar(100) unique not null,
senha varchar(100) not null,
constraint pk_composta_agencia_usuario primary key (id, fk_agencia)
);

create table historico_passagens (
id int primary key auto_increment,
ano int not null,
mes int not null,
empresa_aerea varchar(100) not null,
origem varchar(100) not null,
destino varchar(100) not null,
tarifa double not null,
assentos_comercializados int not null,
constraint chk_mes check (mes > 0 and mes < 13)
);

create table prefil_cliente (
id int primary key auto_increment,
perfil varchar(45),
mais_barato int,
menos_cancelamentos int,
menos_atrasos int,
constraint chk_mais_barato check (mais_barato in (0, 1)),
constraint chk_menos_cancelamentos check (menos_cancelamentos in (0, 1)),
constraint chk_menos_atrasos check (menos_atrasos in (0, 1))
);