var database = require("../database/config");

function listar() {
    var instrucao = `SELECT * FROM usuario;`;
    return database.executar(instrucao);
}

function buscarPorIdUsuario(IdUsuario) {
    var instrucao = `SELECT * FROM usuario WHERE IdUsuario = ${IdUsuario};`;
    return database.executar(instrucao);
}

function cadastrar(nome, email, senha) {
    var instrucao = `
        INSERT INTO usuario (nome, email, senha) VALUES ('${nome}', '${email}', '${senha}');
    `;
    return database.executar(instrucao);
}

function atualizar(IdUsuario, nome, email, telefone) {
    var instrucao = `
        UPDATE usuario 
        SET nome = '${nome}', email = '${email}', telefone = '${telefone}'
        WHERE IdUsuario = ${IdUsuario};
    `;
    return database.executar(instrucao);
}

function deletar(IdUsuario) {
    var instrucao = `DELETE FROM usuario WHERE IdUsuario = ${IdUsuario};`;
    return database.executar(instrucao);
}

module.exports = {
    listar,
    buscarPorIdUsuario,
    cadastrar,
    atualizar,
    deletar
};
