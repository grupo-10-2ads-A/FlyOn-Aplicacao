var express = require("express");
var router = express.Router();
var usuarioController = require("../controllers/usuarioController");

// Rota para cadastrar usuário
router.post("/cadastrar", usuarioController.cadastrar);

// Rota para autenticar usuário
router.post("/autenticar", usuarioController.autenticar);


module.exports = router;
