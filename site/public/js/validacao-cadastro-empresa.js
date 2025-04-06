document.addEventListener("DOMContentLoaded", function () {
  document.querySelector("button").addEventListener("click", function (event) {
      event.preventDefault();

      let nomeFantasia = document.getElementById("nome_fantasia_input").value;
      let cnpj = document.getElementById("cnpj_input").value;
      let email = document.getElementById("email_cadastro_input").value;
      let senha = document.getElementById("senha_cadastro_input").value;
      let representanteLegal = document.getElementById("representante_legal_input").value;
      let razaoSocial = document.getElementById("razao_social_input").value;

       // Dados de endereço
       let cep = document.getElementById("cep_input").value;
       let numero = document.getElementById("numero_input").value;
       let logradouro = document.getElementById("logradouro_input").value;
       let bairro = document.getElementById("bairro_input").value;
       let cidade = document.getElementById("cidade_input").value;
       let estado = document.getElementById("estado_input").value;
 
       // Validações
       validarNomeFantasia(nomeFantasia);
       validarCNPJ(cnpj);
       validarEmail(email);
       validarSenha(senha);
       validarRepresentanteLegal(representanteLegal);
       validarRazaoSocial(razaoSocial);
       validarEndereco(cep, numero, logradouro, bairro, cidade, estado);
  });
});

function validarNomeFantasia(nomeFantasia) {
  if (nomeFantasia.trim().length > 2) {
      return true;
  } else {
      exibirMensagemErro("Erro: Nome fantasia deve conter mais de 2 caracteres.");
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

function validarRepresentanteLegal(representanteLegal) {
  if (representanteLegal.trim().length > 2) {
      return true;
  } else {
      exibirMensagemErro("Erro: Representante legal deve conter mais de 2 caracteres.");
      return false;
  }
}

function validarRazaoSocial(razaoSocial) {
  if (razaoSocial.trim().length > 2) {
      return true;
  } else {
      exibirMensagemErro("Erro: Razão social deve conter mais de 2 caracteres.");
      return false;
  }
}


function validarEndereco(cep, numero, logradouro, bairro, cidade, estado) {
    if (!validarCEP(cep)) {
        return false;
    }
    if (!validarNumero(numero)) {
        return false;
    }
    if (logradouro.trim().length < 3) {
        exibirMensagemErro("Erro: Logradouro deve conter pelo menos 3 caracteres.");
        return false;
    }
    if (bairro.trim().length < 3) {
        exibirMensagemErro("Erro: Bairro deve conter pelo menos 3 caracteres.");
        return false;
    }
    if (cidade.trim().length < 3) {
        exibirMensagemErro("Erro: Cidade deve conter pelo menos 3 caracteres.");
        return false;
    }
    if (estado.trim().length !== 2) {
        exibirMensagemErro("Erro: Estado deve ser representado por 2 caracteres.");
        return false;
    }
    return true;
}

function validarCEP(cep) {
    const regexCEP = /^[0-9]{5}-[0-9]{3}$/;
    if (regexCEP.test(cep)) {
        return true;
    } else {
        exibirMensagemErro("Erro: CEP inválido. Formato correto: 00000-000.");
        return false;
    }
}

function validarNumero(numero) {
    if (numero > 0) {
        return true;
    } else {
        exibirMensagemErro("Erro: Número inválido. Deve ser maior que 0.");
        return false;
    }
}


// Exibição de mensagens
// function exibirMensagemErro(mensagem) {
//   const cardErro = document.getElementById("cardErro");
//   const mensagemErro = document.getElementById("mensagem_erro");
//   cardErro.style.display = "block";
//   cardErro.style.backgroundColor = "red";
//   mensagemErro.innerHTML = mensagem;
// }

// function exibirMensagemSucesso(mensagem) {
//   const cardErro = document.getElementById("cardErro");
//   const mensagemErro = document.getElementById("mensagem_erro");
//   cardErro.style.display = "block";
//   cardErro.style.backgroundColor = "green";
//   mensagemErro.innerHTML = mensagem;
// }

// function sumirMensagem() {
//   const cardErro = document.getElementById("cardErro");
//   cardErro.style.display = "none";
// }
