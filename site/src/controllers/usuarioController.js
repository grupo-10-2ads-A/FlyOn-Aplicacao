var database = require("../database/config");

// Cadastrar usuário
function cadastrar(req, res) {
    console.log("Iniciando validação das informações...");

    var nome = req.body.nome;
    var telefone = req.body.telefone;
    var email = req.body.email;
    var senha = req.body.senha;

    if (!nome || !email || !senha || !telefone) {
        console.log("Erro: Campos obrigatórios não preenchidos.");
        return res.status(400).json({ erro: "Todos os campos são obrigatórios." });
    }

    var instrucaoSql = `INSERT INTO usuario (nome, telefone, email, senha) VALUES ('${nome}', '${telefone}','${email}', '${senha}')`;

    console.log("Executando SQL:", instrucaoSql);

    database.executar(instrucaoSql)
        .then((resultado) => {
            console.log("Usuário cadastrado com sucesso:", resultado);
            res.status(201).json({ mensagem: "Usuário cadastrado com sucesso!", resultado });
        })
        .catch((erro) => {
            console.error("Erro ao cadastrar usuário:", erro);
            res.status(500).json({ erro: "Erro interno ao cadastrar usuário." });
        });
}

// Autenticar usuário
function autenticar(req, res) {
    console.log("Dados recebidos no backend:", req.body);
    
    var email = req.body.emailServer;
    var senha = req.body.senhaServer;

    console.log("E-mail recebido:", email);
    console.log("Senha recebida:", senha);

    var instrucaoSql = `SELECT * FROM usuario WHERE email = '${email}' AND senha = '${senha}'`;
    console.log("Query SQL:", instrucaoSql);

    database.executar(instrucaoSql)
        .then((resultado) => {
            console.log("Resultado da consulta:", resultado);

            if (resultado.length > 0) {
                console.log("Usuário autenticado com sucesso:", resultado[0]);
                res.status(200).json(resultado[0]);
            } else {
                console.log("Nenhum usuário encontrado para as credenciais fornecidas.");
                res.status(401).json({ mensagem: "E-mail ou senha inválidos!" });
            }
        })
        .catch((erro) => {
            console.error("Erro na execução da consulta:", erro);
            res.status(500).json(erro);
        });
}

module.exports = {
    cadastrar,
    autenticar
};
