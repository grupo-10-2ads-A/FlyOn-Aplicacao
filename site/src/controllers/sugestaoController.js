const sugestaoModel = require("../models/sugestaoModel");

function cadastrarSugestao(req, res) {
    console.log(req.body)
    // const { origem, destino, descricao } = req.body;
    const { fkUsuario, descricao } = req.body;
    

    // if (!origem || !destino || !descricao) {
    if(!fkUsuario || !descricao ){
        return res.status(400).json({ erro: "Todos os campos são obrigatórios." });
    }

   sugestaoModel.cadastrarSugestao( fkUsuario, descricao)
        .then(resultado => {
            res.status(201).json({ mensagem: "Sugestão cadastrada com sucesso!", resultado });
        })
        .catch(erro => {
            console.error("Erro ao cadastrar sugestão:", erro);
            res.status(500).json({ erro: "Erro interno ao cadastrar sugestão." });
        });
}

function listarSugestoes(req, res) {
   sugestaoModel.listarSugestoes()
        .then(resultado => res.status(200).json(resultado))
        .catch(erro => res.status(500).json(erro.sqlMessage));
}

module.exports = {
    cadastrarSugestao,
    listarSugestoes
};
