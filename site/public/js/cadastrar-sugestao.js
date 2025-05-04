document.getElementById('btnSalvarSugestao').addEventListener('click', function (event) {
    event.preventDefault(); // Previne o envio padrão do formulário

    // Coleta o valor da descrição
    const descricao = document.getElementById('sugestao').value;
    const fkUsuario = sessionStorage.getItem('ID_USUARIO');

    // Verifica se o campo de descrição foi preenchido
    if (!descricao) {
        alert('O campo de descrição é obrigatório!');
        return;
    }

    // Envia os dados para o backend
    fetch("/sugestao/cadastrar", { 
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            fkUsuario: fkUsuario,
            descricao: descricao, // Envia apenas a descrição
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.mensagem) {
            alert('Sugestão cadastrada com sucesso!');
            window.location.href = './lista-sugestao.html'; // Opcional: redireciona para a página de lista de sugestões
        } else {
            alert('Erro ao cadastrar sugestão.');
        }
    })
    .catch(error => {
        console.error('Erro ao cadastrar sugestão:', error);
        alert('Erro ao cadastrar sugestão.');
    });
});
