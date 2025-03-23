var express = require("express");
var router = express.Router();
var adminController = require("../controllers/adminController");


router.get("/", adminController.listarUsuario);

router.get("/:IdUsuario", adminController.buscarPorIdUsuario);

router.put("/editar/:IdUsuario", adminController.atualizarUsuario);

router.delete("/deletar/:IdUsuario", adminController.deletarUsuario);

router.post("/cadastrar", adminController.cadastrarUsuario);

router.post("/autenticar", adminController.autenticarUsuario);



module.exports = router;
