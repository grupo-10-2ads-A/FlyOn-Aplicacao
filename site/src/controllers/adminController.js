var database = require("../database/config");

function listarUsuario(req, res) {
    console.log("Iniciando listagem de usuários...");
    var instrucaoSql = `SELECT idUsuario, fk_agencia, nome, cargo, email FROM usuario`;
    console.log("SQL para listar usuários:", instrucaoSql);

    database.executar(instrucaoSql)
        .then((resultado) => {
            console.log("Usuários encontrados:", resultado);
            res.status(200).json(resultado);
        })
        .catch((erro) => {
            console.error("Erro ao listar usuários:", erro);
            res.status(500).json(erro);
        });
}

function buscarPorIdUsuario(req, res) {
    var IdUsuario = req.params.IdUsuario;
    console.log("Buscando usuário com Id:", IdUsuario);
    
    var instrucaoSql = `SELECT idUsuario, fk_agencia, nome, cargo, email FROM usuario WHERE idUsuario = ${IdUsuario}`;
    console.log("SQL para buscar usuário:", instrucaoSql);

    database.executar(instrucaoSql)
        .then((resultado) => {
            if (resultado.length > 0) {
                console.log("Usuário encontrado:", resultado[0]);
                res.status(200).json(resultado[0]);
            } else {
                console.log("Usuário não encontrado!");
                res.status(404).json({ mensagem: "Usuário não encontrado!" });
            }
        })
        .catch((erro) => {
            console.error("Erro ao buscar usuário:", erro);
            res.status(500).json(erro);
        });
}

function atualizarUsuario(req, res) {
    let { idUsuario, nome, email, cargo, senha } = req.body;

    if (!idUsuario) {
        return res.status(400).json({ erro: "ID do usuário não fornecido!" });
    }

    var instrucaoSql = `
        UPDATE usuario 
        SET nome = '${nome}', cargo = '${cargo}', email = '${email}', senha = '${senha}' 
        WHERE idUsuario = ${idUsuario};
    `;

    database.executar(instrucaoSql)
        .then(resultado => res.status(200).json({ mensagem: "Usuário atualizado com sucesso!" }))
        .catch(erro => {
            console.error("Erro ao atualizar usuário:", erro);
            res.status(500).json({ erro: "Erro interno ao atualizar usuário." });
        });
}


function deletarUsuario(req, res) {
    var IdUsuario = req.params.IdUsuario;
    console.log("Iniciando o processo de deleção do usuário com Id:", IdUsuario);
    
    if (!IdUsuario) {
        console.warn("Id do usuário não fornecido!");
        return res.status(400).json({ mensagem: "Id do usuário não fornecido." });
    }

    var instrucaoSql = `DELETE FROM usuario WHERE idUsuario = ${IdUsuario}`;
    console.log("SQL para deletar usuário:", instrucaoSql);

    database.executar(instrucaoSql)
        .then(() => {
            console.log("Usuário com Id:", IdUsuario, "deletado com sucesso!");
            res.status(200).json({ mensagem: "Usuário deletado com sucesso!" });
        })
        .catch((erro) => {
            console.error("Erro ao deletar usuário com Id:", IdUsuario, "-", erro);
            res.status(500).json({ mensagem: "Erro ao deletar usuário.", erro });
        });
}

function cadastrarUsuario(req, res) {
    console.log("Iniciando validação das informações...");

    var { fk_agencia, nome, cargo, email, senha } = req.body;

    if (!fk_agencia || !nome || !cargo || !email || !senha) {
        console.log("Erro: Campos obrigatórios não preenchidos.");
        return res.status(400).json({ erro: "Todos os campos são obrigatórios." });
    }

    var instrucaoSql = `INSERT INTO usuario (fk_agencia, nome, cargo, email, senha) VALUES (${fk_agencia}, '${nome}', '${cargo}', '${email}', '${senha}')`;

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

function autenticarUsuario(req, res) {
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
    listarUsuario,
    buscarPorIdUsuario,
    atualizarUsuario,
    deletarUsuario,
    autenticarUsuario,
    cadastrarUsuario
};
