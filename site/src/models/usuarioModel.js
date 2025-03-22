var database = require("../database/config");

function autenticar(email, senha) {
    console.log("Usuário Model: Iniciando autenticação para:", email);
    
    var instrucaoSql = `
        SELECT idUsuario, nome, cnpj, email, senha, tipoUsuario
        FROM usuario 
        WHERE email = '${email}' AND senha = '${senha}';
    `;
    
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function cadastrar(nome, cnpj, email, senha, tipoUsuario) {
    console.log("Usuário Model: Iniciando cadastro para:", nome, email);

    var instrucaoSql = `
        INSERT INTO usuario (nome, cnpj, email, senha, tipoUsuario) 
        VALUES ('${nome}', '${cnpj}', '${email}', '${senha}', '${tipoUsuario}');
    `;
    
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

module.exports = {
    autenticar,
    cadastrar
};
