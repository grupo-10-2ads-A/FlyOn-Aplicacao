function entrar() {
    const email = document.getElementById("email_login_input").value;
    const senha = document.getElementById("senha_login_input").value;

   
    if (!email || !senha) {
      exibirMensagemErro("Preencha todos os campos antes de continuar.");
      return false;
    }


    fetch("/admin/autenticar", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        emailServer: email,
        senhaServer: senha,
      }),
    })
      .then((resposta) => {
        console.log("Status da resposta HTTP:", resposta.status);
        if (resposta.ok) {
          return resposta.json();
        } else {
          return resposta.text().then((texto) => {
            console.error("Erro no login:", texto);
            throw new Error("Email ou senha inválidos.");
          });
        }
      })
      .then((json) => {
        sessionStorage.setItem("EMAIL_USUARIO", json.email);
        sessionStorage.setItem("NOME_USUARIO", json.nome);
        sessionStorage.setItem("ID_USUARIO", json.idUsuario);  
        sessionStorage.setItem("CARGO", json.cargo);
        
        if (json.fk_agencia) {
          sessionStorage.setItem("idAgencia", json.fk_agencia);
          console.log("ID da Agência armazenado:", json.fk_agencia);
        } else {
            console.warn("ID da Agência não foi recebido do backend.");
        }

        console.log("Login bem-sucedido:", json);

     
       if (json.cargo === "admin") {
          setTimeout(() => {
            window.location.href = "../central-gerenciamento.html";
          }, 1000);
        } else {
          setTimeout(() => {
            window.location.href = "../home.html";
          }, 1000);
        }
      })
      .catch((erro) => {
        console.error("Erro ao realizar o login:", erro.message);
        exibirMensagemErro(erro.message || "Erro ao realizar o login. Tente novamente.");
      })

    return false;
  }