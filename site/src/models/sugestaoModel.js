var database = require("../database/config");

function cadastrarSugestao(fkUsuario, descricao) {
    return new Promise((resolve, reject) => {
        const instrucao = 
        `INSERT INTO sugestao (fk_usuario,fk_passagem_status,fk_passagem_tarifa,descricao) VALUES (${fkUsuario},1,1,'${descricao}');`;
        
        // A instrução agora insere apenas a descrição no banco
        database.executar(instrucao)
            .then(resultado => {
                resolve({ mensagem: "Sugestão cadastrada com sucesso!", resultado });
            })
            .catch(erro => {
                console.error("Erro ao cadastrar sugestão no banco:", erro);
                reject({ erro: "Erro ao cadastrar sugestão." });
            });
    });
}

function listarSugestoes() {
    var instrucao = `
        SELECT  s.id,  vsh.origem, vsh.destino, s.descricao
        FROM sugestao s JOIN voo_status_historico vsh
        ON s.fk_passagem_status = vsh.id
        ORDER BY s.id DESC;
    `;
    return database.executar(instrucao);
}

module.exports = {
    cadastrarSugestao,
    listarSugestoes
};

