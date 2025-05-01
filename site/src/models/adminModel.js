var database = require("../database/config");

function listarUsuario() {
    var instrucao = "SELECT idUsuario, fk_agencia, nome, cargo, email FROM usuario;";
    return database.executar(instrucao);
}

function buscarPorIdUsuario(idUsuario) {
    var instrucao = `SELECT idUsuario, fk_agencia, nome, cargo, email FROM usuario WHERE idUsuario = ${idUsuario};`;
    return database.executar(instrucao);
}

function atualizarUsuario(idUsuario, nome, email, cargo, senha) {
    var instrucao = `
        UPDATE usuario 
        SET nome = '${nome}', cargo = '${cargo}', email = '${email}', senha = '${senha}' 
        WHERE idUsuario = ${idUsuario};
    `;
    return database.executar(instrucao);
}

function deletarUsuario(idUsuario) {
    var instrucao = `DELETE FROM usuario WHERE idUsuario = ${idUsuario};`;
    return database.executar(instrucao);
}

function cadastrarUsuario(fk_agencia, nome, cargo, email, senha) {
    var instrucao = `
        INSERT INTO usuario (fk_agencia, nome, cargo, email, senha) 
        VALUES (${fk_agencia}, '${nome}', '${cargo}', '${email}', '${senha}');
    `;
    return database.executar(instrucao);
}

function autenticarUsuario(email, senha) {
    var instrucao = `
        SELECT idUsuario, fk_agencia, nome, cargo, email, senha
        FROM usuario 
        WHERE email = '${email}' AND senha = '${senha}';
    `;
    return database.executar(instrucao);
}

module.exports = {
    listarUsuario,
    buscarPorIdUsuario,
    atualizarUsuario,
    deletarUsuario,
    cadastrarUsuario,
    autenticarUsuario
};
