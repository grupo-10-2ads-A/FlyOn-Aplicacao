var express = require("express");
var router = express.Router();
var sugestaoController = require("../controllers/sugestaoController");


router.post("/cadastrar", sugestaoController.cadastrarSugestao);

router.get("/listar", sugestaoController.listarSugestoes);

module.exports = router;
