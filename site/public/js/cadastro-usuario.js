document.getElementById("btnCadastrar").addEventListener("click", cadastrarUsuario);


function cadastrarUsuario(event) {
    event.preventDefault();
    const nomeVar = document.getElementById("nome_input").value;
    const cargoVar = document.getElementById("cargo_input").value;
    const emailVar = document.getElementById("email_cadastro_input").value;
    const senhaVar = document.getElementById("senha_cadastro_input").value;

    const idAgencia = sessionStorage.getItem("idAgencia");

    console.log(nomeVar,cargoVar,emailVar,senhaVar,idAgencia)

    fetch("/admin/cadastrar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            nome: nomeVar,
            cargo: cargoVar,
            email: emailVar,
            senha: senhaVar,
            fk_agencia: idAgencia, 
        }),
    })
    .then((resposta) => {
        if (resposta.ok) {
            exibirMensagemSucesso("Cadastro realizado com sucesso! Redirecionando...");
            setTimeout(() => {
                window.location.href = "lista-usuario.html";
            }, 2000);
            limparFormularioCadastro();
        } else {
            resposta.text().then((texto) => {
                console.error("Erro no cadastro:", texto);
                exibirMensagemErro("Erro ao realizar o cadastro. Tente novamente.");
            });
        }
    })
    .catch((erro) => {
        console.error("Erro ao realizar o cadastro:", erro);
        exibirMensagemErro("Erro ao realizar o cadastro. Tente novamente.");
    });
}    

function limparFormularioCadastro() {
    document.getElementById("nome_input").value = "";
    document.getElementById("cargo_input").value = "";
    document.getElementById("email_cadastro_input").value = "";
    document.getElementById("senha_cadastro_input").value = "";
}
