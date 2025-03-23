function deletarUsuario(IdUsuario) {
    if (!IdUsuario) {
        console.error("ID do usuário não foi fornecido!");
        return;
    }

    if (confirm("Tem certeza que deseja excluir este usuário?")) {
        console.log("Deletando usuário com ID:", IdUsuario);

        fetch(`/admin/deletar/${IdUsuario}`, { method: "DELETE" })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao excluir usuário");
            return response.json();
        })
        .then(() => {
            alert("Usuário excluído com sucesso!");
            // Após excluir, remove o usuário do sessionStorage, caso necessário
            let listaUsuarios = JSON.parse(sessionStorage.getItem("LISTA_USUARIOS"));
            listaUsuarios = listaUsuarios.filter(usuario => usuario.IdUsuario !== IdUsuario);
            sessionStorage.setItem("LISTA_USUARIOS", JSON.stringify(listaUsuarios));

            location.reload(); 
        })
        .catch(error => console.error("Erro ao excluir usuário:", error));
    }
}
