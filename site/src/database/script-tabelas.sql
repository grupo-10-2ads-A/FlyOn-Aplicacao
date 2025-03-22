create database teste;
use teste;

create table usuario(
IdUsuario int auto_increment primary key,
nome varchar(45),
telefone char(12),
email varchar(45),
senha varchar(10)
);

select * from usuario;

alter table usuario add column tipoUsuario varchar(25);

update usuario set tipoUsuario = "admin" where idUsuario = 1;

SELECT *
        FROM usuario 
        WHERE email = 'joana@gmail.com' AND senha = '123123'
