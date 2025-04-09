function cadastrarEmpresa(event) {
    event.preventDefault();

    const nomeFantasia = document.getElementById("nome_fantasia_input").value;
    const razaoSocial = document.getElementById("razao_social_input").value;
    const codigo = Math.floor(10000 + Math.random() * 90000);
    const cnpj = document.getElementById("cnpj_input").value;
    const representanteLegal = document.getElementById("representante_legal_input").value;
    const email = document.getElementById("email_cadastro_input").value;
    const senha = document.getElementById("senha_cadastro_input").value;

    // Dados de endereço
    const cep = document.getElementById("cep_input").value;
    const numero = document.getElementById("numero_input").value;
    const logradouro = document.getElementById("logradouro_input").value;
    const bairro = document.getElementById("bairro_input").value;
    const cidade = document.getElementById("cidade_input").value;
    const estado = document.getElementById("estado_input").value;

    console.log(nomeFantasia, razaoSocial, codigo, cnpj, representanteLegal, email, senha, cep, numero, logradouro, bairro, cidade, estado);

    fetch("/agencia/cadastrar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            nome_fantasia: nomeFantasia,
            razao_social: razaoSocial,
            codigo: codigo,
            cnpj: cnpj,
            representante_legal: representanteLegal,
            email: email,
            senha: senha,
            endereco: {
                cep: cep,
                numero: numero,
                logradouro: logradouro,
                bairro: bairro,
                cidade: cidade,
                estado: estado
            }
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


function exibirMensagemSucesso(mensagem) {
    const popup = document.getElementById("popup-sucesso");
    const mensagemElemento = document.getElementById("popup-mensagem");

    mensagemElemento.textContent = mensagem;
    popup.classList.remove("hidden");
    popup.classList.add("show");


    setTimeout(() => {
        popup.classList.remove("show");
        popup.classList.add("hidden");
        window.location.href = "login.html";
    }, 2000);
}

function exibirMensagemErro(mensagem) {
    const popup = document.getElementById("popup");
    const popupMsg = document.getElementById("popup-message");

    popupMsg.innerText = mensagem;
    popup.classList.add("show");

    setTimeout(() => {
        popup.classList.remove("show");
    }, 3000);
}

function limparFormularioCadastro() {
    document.querySelector(".form").reset();
}