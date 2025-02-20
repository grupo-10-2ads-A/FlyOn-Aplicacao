var express = require("express");
var router = express.Router();
var adminController = require("../controllers/adminController");


// Rota para listar todos os usuários (somente admin pode acessar)
router.get("/", adminController.listar);

// Rota para buscar um usuário específico por ID
router.get("/:IdUsuario", adminController.buscarPorIdUsuario);

// Rota para atualizar dados do usuário
router.put("/editar/:IdUsuario", adminController.atualizar);

// Rota para deletar usuário
router.delete("/deletar/:IdUsuario", adminController.deletar);

module.exports = router;
