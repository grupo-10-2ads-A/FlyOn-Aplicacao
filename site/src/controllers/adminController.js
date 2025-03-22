var database = require("../database/config");

// Listar todos os usuários (somente admin pode acessar)
function listar(req, res) {
    console.log("Iniciando listagem de usuários...");
    var instrucaoSql = `SELECT IdUsuario, nome, email, telefone, tipoUsuario FROM usuario`;
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

// Buscar usuário por IdUsuario
function buscarPorIdUsuario(req, res) {
    var IdUsuario = req.params.IdUsuario;
    console.log("Buscando usuário com Id:", IdUsuario);
    
    var instrucaoSql = `SELECT IdUsuario, nome, email, telefone, tipoUsuario FROM usuario WHERE IdUsuario = ${IdUsuario}`;
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


// Atualizar dados do usuário
function atualizar(req, res) {
    var IdUsuario = req.params.IdUsuario;
    var { nome, email, tipoUsuario, telefone } = req.body;

    // Log dos dados recebidos
    console.log("Atualizando usuário com ID:", IdUsuario);
    console.log("Dados recebidos:", { nome, email, tipoUsuario, telefone });

    // Verificar se todos os dados necessários estão presentes
    if (!nome || !email || !tipoUsuario || !telefone) {
        console.error("Todos os campos são obrigatórios!");
        return res.status(400).json({ mensagem: "Todos os campos são obrigatórios!" });
    }

    var instrucaoSql = `UPDATE usuario SET nome = '${nome}', email = '${email}', tipoUsuario = '${tipoUsuario}', telefone = '${telefone}' WHERE IdUsuario = ${IdUsuario}`;
    console.log("SQL para atualizar usuário:", instrucaoSql);

    database.executar(instrucaoSql)
        .then((resultado) => {
            // Verifique se a atualização afetou alguma linha
            if (resultado.affectedRows > 0) {
                console.log("Usuário atualizado com sucesso!");
                res.status(200).json({ mensagem: "Usuário atualizado com sucesso!" });
            } else {
                console.log("Usuário não encontrado para atualização!");
                res.status(404).json({ mensagem: "Usuário não encontrado!" });
            }
        })
        .catch((erro) => {
            console.error("Erro ao atualizar usuário:", erro);
            res.status(500).json(erro);
        });
}

// Deletar usuário
function deletar(req, res) {
    var IdUsuario = req.params.IdUsuario;
    console.log("Iniciando o processo de deleção do usuário com Id:", IdUsuario);
    
    // Verifica se o IdUsuario é válido
    if (!IdUsuario) {
        console.warn("Id do usuário não fornecido!");
        return res.status(400).json({ mensagem: "Id do usuário não fornecido." });
    }

    var instrucaoSql = `DELETE FROM usuario WHERE IdUsuario = ${IdUsuario}`;
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

module.exports = {
    listar,
    buscarPorIdUsuario,
    atualizar,
    deletar
};
