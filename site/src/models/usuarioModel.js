var database = require("../database/config");

function autenticar(email, senha) {
    console.log("Usuário Model: Iniciando autenticação para:", email);
    
    var instrucaoSql = `
        SELECT idUsuario, nome, telefone, email, senha, tipoUsuario
        FROM usuario 
        WHERE email = '${email}' AND senha = '${senha}';
    `;
    
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function cadastrar(nome, telefone, email, senha, tipoUsuario) {
    console.log("Usuário Model: Iniciando cadastro para:", nome, email);

    var instrucaoSql = `
        INSERT INTO usuario (nome, telefone, email, senha, tipoUsuario) 
        VALUES ('${nome}', '${telefone}', '${email}', '${senha}', ''${tipoUsuario});
    `;
    
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

module.exports = {
    autenticar,
    cadastrar
};
