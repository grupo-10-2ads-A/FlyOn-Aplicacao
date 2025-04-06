function cadastrarEmpresa(event) {
    event.preventDefault();

    
    const razaoSocial = document.getElementById("razao_social_input").value;
    const codigo = document.getElementById("codigo_input").value;
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

    console.log(razaoSocial, codigo, cnpj, representanteLegal, email, senha, cep, numero, logradouro, bairro, cidade, estado);

    fetch("/agencia/cadastrar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
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
                window.location.href = "./auth/login.html"; 
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
