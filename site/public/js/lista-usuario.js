document.addEventListener("DOMContentLoaded", () => {
    fetch("/admin/") 
        .then(response => response.json())
        .then(usuarios => {
            sessionStorage.setItem("LISTA_USUARIOS", JSON.stringify(usuarios));
            console.log("Usuários armazenados no sessionStorage:", usuarios);

            let tabela = document.getElementById("tabelaUsuarios");
            tabela.innerHTML = ""; 

            usuarios.forEach(usuario => {
                let linha = document.createElement("tr");

                linha.innerHTML = `
                    <td>${usuario.nome}</td>
                    <td>${usuario.email}</td>
                    <td>${usuario.cargo}</td>
                    <td>${usuario.cnpj}</td>
                    <td>
                        <button onclick="editarUsuario(${usuario.idUsuario})">Editar</button>
                        <button onclick="deletarUsuario(${usuario.idUsuario})">Excluir</button>
                    </td>
                `;
                tabela.appendChild(linha);

            
                sessionStorage.setItem(`USUARIO_${usuario.idUsuario}`, JSON.stringify(usuario));
            });
        })
        .catch(error => console.error("Erro ao buscar usuários:", error));
});


function editarUsuario(IdUsuario) {
    window.location.href = `/editar-usuario.html?IdUsuario=${IdUsuario}`;
}


