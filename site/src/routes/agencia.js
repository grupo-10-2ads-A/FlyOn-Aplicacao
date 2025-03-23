var express = require("express");
var router = express.Router();
var agenciaController = require("../controllers/agenciaController");

router.post("/cadastrar", agenciaController.cadastrarAgencia);

module.exports = router;
