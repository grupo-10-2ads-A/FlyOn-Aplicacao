function entrar() {
    const email = document.getElementById("email_login_input").value;
    const senha = document.getElementById("senha_login_input").value;

   
    if (!email || !senha) {
      exibirMensagemErro("Preencha todos os campos antes de continuar.");
      return false;
    }


    fetch("/usuarios/autenticar", {
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
        sessionStorage.setItem("TIPO_USUARIO", json.tipoUsuario);

        console.log("Login bem-sucedido:", json);

     
       if (json.tipoUsuario === "admin") {
          setTimeout(() => {
            window.location.href = "../gerenciamento-admin.html";
          }, 1000);
        } else {
          setTimeout(() => {
            window.location.href = "../bem-vindos.html";
          }, 1000);
        }
      })
      .catch((erro) => {
        console.error("Erro ao realizar o login:", erro.message);
        exibirMensagemErro(erro.message || "Erro ao realizar o login. Tente novamente.");
      })

    return false;
  }