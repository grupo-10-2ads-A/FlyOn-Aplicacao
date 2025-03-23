document.addEventListener("DOMContentLoaded", () => {
    // Obtém o ID do usuário da URL
    const params = new URLSearchParams(window.location.search);
    let IdUsuario = params.get("IdUsuario");

    if (!IdUsuario) {
        console.error("ID do usuário não encontrado na URL!");
        return;
    }

    console.log("ID do usuário para edição:", IdUsuario);

    // Buscar dados do usuário para preencher o formulário
    fetch(`/admin/editar/${IdUsuario}`)
        .then(response => {
            if (!response.ok) throw new Error("Erro ao buscar usuário");
            return response.json();
        })
        .then(usuario => {
            console.log("Usuário carregado:", usuario);

            document.getElementById("IdUsuario").value = usuario.idUsuario;
            document.getElementById("nome").value = usuario.nome;
            document.getElementById("email").value = usuario.email;
            document.getElementById("cargo").value = usuario.cargo;
            document.getElementById("senha").value = usuario.senha;
        })
        .catch(error => console.error("Erro ao carregar dados do usuário:", error));

    // Evento de submit para atualizar usuário
    document.getElementById("formEditarUsuario").addEventListener("submit", (event) => {
        event.preventDefault();

        const novoNome = document.getElementById("nome").value;
        const novoEmail = document.getElementById("email").value;
        const novoCargo = document.getElementById("cargo").value;
        const novaSenha = document.getElementById("senha").value;

        // Validação simples
        if (!novoNome || !novoEmail || !novoCargo || !novaSenha) {
            alert("Todos os campos devem ser preenchidos!");
            return;
        }

        fetch(`/admin/editar/${IdUsuario}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                idUsuario: IdUsuario,
                nome: novoNome,
                email: novoEmail,
                cargo: novoCargo,
                senha: novaSenha
            })
        })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao editar usuário");
            return response.json();
        })
        .then(() => {
            alert("Usuário atualizado com sucesso!");
            window.location.href = "lista-usuario.html";
        })
        .catch(error => console.error("Erro ao editar usuário:", error));
    });
});
