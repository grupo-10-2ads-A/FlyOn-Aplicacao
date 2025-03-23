var database = require("../database/config");

function listarUsuario() {
    var instrucao = "SELECT * FROM usuario;";
    return database.executar(instrucao);
}

function buscarPorIdUsuario(IdUsuario) {
    var instrucao = `SELECT * FROM usuario WHERE idUsuario = ${IdUsuario};`;
    return database.executar(instrucao);
}

function atualizarUsuario(IdUsuario, nome, cargo, email, senha) {
    var instrucao = `
        UPDATE usuario 
        SET nome = '${nome}', cargo = '${cargo}', email = '${email}', senha = '${senha}' 
        WHERE idUsuario = ${IdUsuario};
    `;
    return database.executar(instrucao);
}

function deletarUsuario(IdUsuario) {
    var instrucao = `DELETE FROM usuario WHERE idUsuario = ${IdUsuario};`;
    return database.executar(instrucao);
}

function autenticarUsuario(email, senha) {
    console.log("Usuário Model: Iniciando autenticação para:", email);

    var instrucaoSql = `
        SELECT idUsuario, fk_agencia, nome, cargo, email, senha
        FROM usuario 
        WHERE email = '${email}' AND senha = '${senha}';
    `;
    
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function cadastrarUsuario(fk_agencia, nome, cargo, email, senha) {
    console.log("Usuário Model: Iniciando cadastro para:", nome, email);

    var instrucaoSql = `
        INSERT INTO usuario (fk_agencia, nome, cargo, email, senha) 
        VALUES (${fk_agencia}, '${nome}', '${cargo}', '${email}', '${senha}');
    `;
    
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

module.exports = {
    listarUsuario,
    buscarPorIdUsuario,
    atualizarUsuario,
    deletarUsuario,
    autenticarUsuario,
    cadastrarUsuario
};
