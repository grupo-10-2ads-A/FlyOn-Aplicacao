var agenciaModel = require("../models/agenciaModel");

function cadastrarAgencia(req, res) {
    var { nome_fantasia, representante_legal, razao_social, cnpj, email, senha, endereco } = req.body;
    var { cep, numero, logradouro, bairro, cidade, estado } = endereco;

    if (!nome_fantasia || !email || !senha || !cnpj || !representante_legal || !razao_social || !cep || !numero || !logradouro || !bairro || !cidade || !estado) {
        return res.status(400).json({ erro: "Todos os campos são obrigatórios." });
    }

    agenciaModel.cadastrarAgencia({ nome_fantasia, representante_legal, razao_social, cnpj, email, senha, endereco })
        .then(() => {
            res.status(201).json({ mensagem: "Agência, Endereço e Usuário cadastrados com sucesso!" });
        })
        .catch((erro) => {
            console.error("Erro no cadastro:", erro);
            res.status(500).json({ erro: "Erro interno ao cadastrar agência, endereço ou usuário." });
        });
}

module.exports = {
    cadastrarAgencia
};
