const adminModel = require("../models/adminModel");

function listarUsuario(req, res) {
    adminModel.listarUsuario()
        .then(resultado => res.status(200).json(resultado))
        .catch(erro => {
            console.error("Erro ao listar usuários:", erro);
            res.status(500).json({ erro: "Erro interno ao listar usuários." });
        });
}

function buscarPorIdUsuario(req, res) {
    const idUsuario = req.params.IdUsuario;

    adminModel.buscarPorIdUsuario(idUsuario)
        .then(resultado => {
            if (resultado.length > 0) {
                res.status(200).json(resultado[0]);
            } else {
                res.status(404).json({ mensagem: "Usuário não encontrado!" });
            }
        })
        .catch(erro => {
            console.error("Erro ao buscar usuário:", erro);
            res.status(500).json({ erro: "Erro ao buscar usuário." });
        });
}

function atualizarUsuario(req, res) {
    const { idUsuario, nome, email, cargo, senha } = req.body;

    if (!idUsuario) {
        return res.status(400).json({ erro: "ID do usuário não fornecido!" });
    }

    adminModel.atualizarUsuario(idUsuario, nome, email, cargo, senha)
        .then(() => res.status(200).json({ mensagem: "Usuário atualizado com sucesso!" }))
        .catch(erro => {
            console.error("Erro ao atualizar usuário:", erro);
            res.status(500).json({ erro: "Erro interno ao atualizar usuário." });
        });
}

function deletarUsuario(req, res) {
    const idUsuario = req.params.IdUsuario;

    if (!idUsuario) {
        return res.status(400).json({ mensagem: "ID do usuário não fornecido." });
    }

    adminModel.deletarUsuario(idUsuario)
        .then(() => res.status(200).json({ mensagem: "Usuário deletado com sucesso!" }))
        .catch(erro => {
            console.error("Erro ao deletar usuário:", erro);
            res.status(500).json({ erro: "Erro interno ao deletar usuário." });
        });
}

function cadastrarUsuario(req, res) {
    const { fk_agencia, nome, cargo, email, senha } = req.body;

    if (!fk_agencia || !nome || !cargo || !email || !senha) {
        return res.status(400).json({ erro: "Todos os campos são obrigatórios." });
    }

    adminModel.cadastrarUsuario(fk_agencia, nome, cargo, email, senha)
        .then(resultado => res.status(201).json({ mensagem: "Usuário cadastrado com sucesso!", resultado }))
        .catch(erro => {
            console.error("Erro ao cadastrar usuário:", erro);
            res.status(500).json({ erro: "Erro interno ao cadastrar usuário." });
        });
}

function autenticarUsuario(req, res) {
    const { emailServer: email, senhaServer: senha } = req.body;

    adminModel.autenticarUsuario(email, senha)
        .then(resultado => {
            if (resultado.length > 0) {
                res.status(200).json(resultado[0]);
            } else {
                res.status(401).json({ mensagem: "E-mail ou senha inválidos!" });
            }
        })
        .catch(erro => {
            console.error("Erro na autenticação:", erro);
            res.status(500).json({ erro: "Erro interno ao autenticar usuário." });
        });
}

module.exports = {
    listarUsuario,
    buscarPorIdUsuario,
    atualizarUsuario,
    deletarUsuario,
    cadastrarUsuario,
    autenticarUsuario
};
