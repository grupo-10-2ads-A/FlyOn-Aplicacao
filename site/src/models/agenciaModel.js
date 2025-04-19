var database = require("../database/config");

function cadastrarAgencia({ nome_fantasia, representante_legal, razao_social, cnpj, email, senha, endereco }) {
    var { cep, numero, logradouro, bairro, cidade, estado } = endereco;

    var instrucaoSqlAgencia = `
        INSERT INTO agencia (cnpj, nome_fantasia, razao_social, representante_legal)
        VALUES ('${cnpj}', '${nome_fantasia}', '${razao_social}', '${representante_legal}');
    `;

    return database.executar(instrucaoSqlAgencia)
        .then((resultadoAgencia) => {
            var idAgencia = resultadoAgencia.insertId;

            var instrucaoSqlEndereco = `
                INSERT INTO endereco (fk_agencia, cep, numero, logradouro, bairro, cidade, estado)
                VALUES ('${idAgencia}', '${cep}', '${numero}', '${logradouro}', '${bairro}', '${cidade}', '${estado}');
            `;

            return database.executar(instrucaoSqlEndereco)
                .then(() => {
                    var instrucaoSqlUsuario = `
                        INSERT INTO usuario (fk_agencia, nome, cargo, email, senha)
                        VALUES ('${idAgencia}', '${representante_legal}', 'admin', '${email}', '${senha}');
                    `;
                    return database.executar(instrucaoSqlUsuario);
                });
        });
}

module.exports = {
    cadastrarAgencia
};
