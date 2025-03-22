const pwShowHide = document.querySelectorAll(".showHidePw"), 
pwFields = document.querySelectorAll(".password input"); // Pegando os inputs corretamente

pwShowHide.forEach((eyeIcon, index) => {
  eyeIcon.addEventListener("click", () => {
      const pwField = pwFields[index];

      if (pwField.type === "password") {
          pwField.type = "text"; 
          eyeIcon.classList.replace("uil-eye-slash", "uil-eye");
      } else {
          pwField.type = "password"; 
          eyeIcon.classList.replace("uil-eye", "uil-eye-slash");
      }
  });
});

function cadastrar(event) {
    event.preventDefault(); 

    const nomeVar = document.getElementById("nome_input").value;
    const cnpjVar = document.getElementById("cnpj_input").value;
    const emailVar = document.getElementById("email_cadastro_input").value;
    const senhaVar = document.getElementById("senha_cadastro_input").value;

  
    if (
        !validarNome(nomeVar) ||
        !validarcnpj(cnpjVar) ||
        !validarEmail(emailVar) ||
        !validarSenha(senhaVar)
    ) {
        return false;
    }
   
   
    
    fetch("/usuarios/cadastrar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            nome: nomeVar,
            cnpj: cnpjVar,
            email: emailVar,
            senha: senhaVar,
        }),
    })
    .then((resposta) => {
     
        if (resposta.ok) {
            exibirMensagemSucesso("Cadastro realizado com sucesso! Redirecionando...");
            setTimeout(() => {
                window.location.href = "login.html"; 
            }, 2000); 
            limparFormularioCadastro();
        } else {
            resposta.text().then((texto) => console.error("Erro no cadastro:", texto));
            throw "Houve um erro ao tentar realizar o cadastro!";
        }
    })
    .catch((erro) => {
        exibirMensagemErro("Erro ao realizar o cadastro. Tente novamente.");
    });
}


function limparFormularioCadastro() {
    document.getElementById("nome_input").value = "";
    document.getElementById("cnpj_input").value = "";
    document.getElementById("email_cadastro_input").value = "";
    document.getElementById("senha_cadastro_input").value = "";
}
