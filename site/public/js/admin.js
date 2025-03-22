document.addEventListener("DOMContentLoaded", () => {
    fetch("/admin/") 
        .then(response => response.json())
        .then(usuarios => {
            console.log("Usuários recebidos:", usuarios);
            let tabela = document.getElementById("tabelaUsuarios");
            tabela.innerHTML = ""; 

            usuarios.forEach(usuario => {
                let linha = document.createElement("tr");

                linha.innerHTML = `
                    <td>${usuario.nome}</td>
                    <td>${usuario.email}</td>
                    <td>${usuario.tipoUsuario}</td>
                    <td>${usuario.telefone}</td>
                    <td>
                        <button onclick="editarUsuario(${usuario.IdUsuario})">Editar</button>
                        <button onclick="deletarUsuario(${usuario.IdUsuario})">Excluir</button>
                    </td>
                `;
                tabela.appendChild(linha);
            });
        })
        .catch(error => console.error("Erro ao buscar usuários:", error));
});

function editarUsuario(IdUsuario) {
    window.location.href = `/editar-usuario.html?IdUsuario=${IdUsuario}`;
}


function deletarUsuario(IdUsuario) {
    if (confirm("Tem certeza que deseja excluir este usuário?")) {
        fetch(`/admin/deletar/${IdUsuario}`, { method: "DELETE" })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao excluir usuário");
            return response.json();
        })
        .then(() => location.reload())
        .catch(error => console.error("Erro ao excluir usuário:", error));
    }
}
