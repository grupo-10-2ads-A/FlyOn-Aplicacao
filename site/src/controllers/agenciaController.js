var database = require("../database/config");

function cadastrarAgencia(req, res) {
    console.log("Iniciando validação das informações...");

    var { representante_legal, codigo, razao_social, cnpj, email, senha, endereco } = req.body;
    var { cep, numero, logradouro, bairro, cidade, estado } = endereco;

    if ( !email || !senha || !cnpj || !codigo || !representante_legal || !razao_social || !cep || !numero || !logradouro || !bairro || !cidade || !estado) {
        console.log("Erro: Campos obrigatórios não preenchidos.");
        return res.status(400).json({ erro: "Todos os campos são obrigatórios." });
    }

    // Primeira Query: Inserção na tabela agencia
    var instrucaoSqlAgencia = `
        INSERT INTO agencia (codigo, cnpj, razao_social, representante_legal) 
        VALUES ('${codigo}', '${cnpj}', '${razao_social}', '${representante_legal}');
    `;

    database.executar(instrucaoSqlAgencia)
        .then((resultadoAgencia) => {
            console.log("Agência cadastrada com sucesso:", resultadoAgencia);

            var idAgencia = resultadoAgencia.insertId;

            // Segunda Query: Inserção na tabela endereco, utilizando o idAgencia como chave estrangeira
            var instrucaoSqlEndereco = `
                INSERT INTO endereco (fk_agencia, cep, numero, logradouro, bairro, cidade, estado) 
                VALUES ('${idAgencia}', '${cep}', '${numero}', '${logradouro}', '${bairro}', '${cidade}', '${estado}');
            `;

            return database.executar(instrucaoSqlEndereco)
                .then((resultadoEndereco) => {
                    console.log("Endereço cadastrado com sucesso:", resultadoEndereco);
                    // Passar o idAgencia para a próxima query
                    return { idAgencia, resultadoEndereco };
                });
        })
        .then(({ idAgencia }) => {
            // Terceira Query: Inserção na tabela usuario
            var instrucaoSqlUsuario = `
                INSERT INTO usuario (fk_agencia, nome, cargo, email, senha) 
                VALUES ('${idAgencia}', '${representante_legal}', 'admin', '${email}', '${senha}');
            `;
            
            return database.executar(instrucaoSqlUsuario);
        })
        .then((resultadoUsuario) => {
            console.log("Usuário cadastrado com sucesso:", resultadoUsuario);
            res.status(201).json({ mensagem: "Agência, Endereço e Usuário cadastrados com sucesso!" });
        })
        .catch((erro) => {
            console.error("Erro ao cadastrar agência, endereço ou usuário:", erro);
            res.status(500).json({ erro: "Erro interno ao cadastrar agência, endereço ou usuário." });
        });
}

module.exports = {
    cadastrarAgencia,
};