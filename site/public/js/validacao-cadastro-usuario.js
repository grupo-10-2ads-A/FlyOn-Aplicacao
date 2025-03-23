
document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("button").addEventListener("click", function (event) {
        event.preventDefault();
  
        let nome = document.getElementById("nome_input").value;
        let cargo = document.getElementById("cargo_input").value;
        let email = document.getElementById("email_cadastro_input").value;
        let senha = document.getElementById("senha_cadastro_input").value;
  
        validarNome(nome);
        validarCargo(cargo);
        validarEmail(email);
        validarSenha(senha);
    });
  });
  
  function validarNome(nome) {
    if (nome.trim().length > 2) {
        return true;
    } else {
        exibirMensagemErro("Erro: Nome deve conter mais de 2 caracteres.");
        return false;
    }
  }
  
  function validarCargo(cargo) {
    if (cargo.trim().length > 2) {
        return true;
    } else {
        exibirMensagemErro("Erro: Cargo deve conter mais de 2 caracteres.");
        return false;
    }
  }
  
  function validarEmail(email) {
    const regexEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (regexEmail.test(email)) {
        return true;
    } else {
        exibirMensagemErro("Erro: E-mail inválido. Deve conter '@' e '.'");
        return false;
    }
  }
  
  function validarSenha(senha) {
    if (senha.length >= 6) {
        return true;
    } else {
        exibirMensagemErro("Erro: Senha deve conter pelo menos 6 caracteres.");
        return false;
    }
  }

  
 
//   function exibirMensagemErro(mensagem) {
//     const cardErro = document.getElementById("cardErro");
//     const mensagemErro = document.getElementById("mensagem_erro");
//     cardErro.style.display = "block";
//     cardErro.style.backgroundColor = "red";
//     mensagemErro.innerHTML = mensagem;
//   }
  
//   function exibirMensagemSucesso(mensagem) {
//     const cardErro = document.getElementById("cardErro");
//     const mensagemErro = document.getElementById("mensagem_erro");
//     cardErro.style.display = "block";
//     cardErro.style.backgroundColor = "green";
//     mensagemErro.innerHTML = mensagem;
//   }
  

//   function sumirMensagem() {
//     const cardErro = document.getElementById("cardErro");
//     cardErro.style.display = "none";
//   }
  