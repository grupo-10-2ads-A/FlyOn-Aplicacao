document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("button").addEventListener("click", function (event) {
        event.preventDefault();

        let nome = document.getElementById("nome_input").value;
        let cnpj = document.getElementById("cnpj_input").value;
        let email = document.getElementById("email_cadastro_input").value;
        let senha = document.getElementById("senha_cadastro_input").value;

        validarNome(nome);
        validarCNPJ(cnpj);
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
  function validarCNPJ(cnpj) {
    cnpj = cnpj.replace(/[^\d]/g, ""); 
    
    if (cnpj.length !== 14) {
        exibirMensagemErro("Erro: CNPJ deve conter 14 dígitos.");
        return false;
    }

   
    if (/^(\d)\1+$/.test(cnpj)) {
        exibirMensagemErro("Erro: CNPJ inválido.");
        return false;
    }

  
    let tamanho = cnpj.length - 2;
    let numeros = cnpj.substring(0, tamanho);
    let digitos = cnpj.substring(tamanho);
    let soma = 0;
    let pos = tamanho - 7;

    for (let i = tamanho; i >= 1; i--) {
        soma += numeros.charAt(tamanho - i) * pos--;
        if (pos < 2) pos = 9;
    }

    let resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
    if (resultado != digitos.charAt(0)) {
        exibirMensagemErro("Erro: CNPJ inválido.");
        return false;
    }

    tamanho = tamanho + 1;
    numeros = cnpj.substring(0, tamanho);
    soma = 0;
    pos = tamanho - 7;

    for (let i = tamanho; i >= 1; i--) {
        soma += numeros.charAt(tamanho - i) * pos--;
        if (pos < 2) pos = 9;
    }

    resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
    if (resultado != digitos.charAt(1)) {
        exibirMensagemErro("Erro: CNPJ inválido.");
        return false;
    }

    return true;
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
  
  // Exibição de mensagens
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