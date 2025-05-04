document.addEventListener("DOMContentLoaded", function () {
    fetch("/sugestao/listar") // esse é o endpoint do seu backend
      .then((res) => res.json())
      .then((data) => {
        const tabela = document.getElementById("tabelaSugestao");
  
        if (!Array.isArray(data) || data.length === 0) {
          tabela.innerHTML = `<tr><td colspan="4">Nenhuma sugestão encontrada.</td></tr>`;
          return;
        }
  
        data.forEach((sugestao) => {
          const linha = document.createElement("tr");
          linha.innerHTML = `
            <td>-</td>
            <td>${sugestao.origem}</td>
            <td>${sugestao.destino}</td>
            <td>${sugestao.descricao}</td>
          `;
          tabela.appendChild(linha);
        });
      })
      .catch((erro) => {
        console.error("Erro ao carregar sugestões:", erro);
        document.getElementById("tabelaSugestao").innerHTML =
          `<tr><td colspan="4">Erro ao carregar sugestões.</td></tr>`;
      });
  });
  