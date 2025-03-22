var database = require("../database/config");

function listar() {
    var instrucao = `SELECT * FROM usuario;`;
    return database.executar(instrucao);
}

function buscarPorIdUsuario(IdUsuario) {
    var instrucao = `SELECT * FROM usuario WHERE IdUsuario = ${IdUsuario};`;
    return database.executar(instrucao);
}


function atualizar(IdUsuario, nome, email, cnpj) {
    var instrucao = `
        UPDATE usuario 
        SET nome = '${nome}', email = '${email}', cnpj = '${cnpj}'
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
    atualizar,
    deletar
};
