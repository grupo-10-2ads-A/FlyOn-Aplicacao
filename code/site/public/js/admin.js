document.addEventListener("DOMContentLoaded", () => {
    fetch("/usuarios")
        .then(response => response.json())
        .then(usuarios => {
            let tabela = document.getElementById("tabelaUsuarios");
            usuarios.forEach(usuario => {
                let linha = document.createElement("tr");

                linha.innerHTML = `
                    <td>${usuario.nome}</td>
                    <td>${usuario.email}</td>
                    <td>${usuario.nivel_acesso}</td>
                    <td>
                        <button onclick="editarUsuario(${usuario.id})">Editar</button>
                        <button onclick="deletarUsuario(${usuario.id})">Excluir</button>
                    </td>
                `;
                tabela.appendChild(linha);
            });
        });
});

function editarUsuario(id) {
    let novoNome = prompt("Digite o novo nome:");
    let novoEmail = prompt("Digite o novo email:");
    let novoNivel = prompt("Digite o novo nível de acesso:");

    if (novoNome && novoEmail && novoNivel) {
        fetch(`/usuarios/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome: novoNome, email: novoEmail, nivel_acesso: novoNivel })
        })
        .then(response => response.json())
        .then(() => location.reload());
    }
}

function deletarUsuario(id) {
    if (confirm("Tem certeza que deseja excluir este usuário?")) {
        fetch(`/usuarios/${id}`, { method: "DELETE" })
        .then(response => response.json())
        .then(() => location.reload());
    }
}
