function modalEquipe() {
    const modal = document.getElementById("modal");
    const modalImg = document.getElementById("modal-img");
    const modalName = document.getElementById("modal-name");
    const modalRole = document.getElementById("modal-role");
    const modalDesc = document.getElementById("modal-desc");
    const modalLinkedin = document.getElementById("modal-linkedin");
    const modalGithub = document.getElementById("modal-github");
    const closeModal = document.querySelector(".close");
  
    const equipe = {
      0: {
        nome: "Ariel",
        cargo: "Desenvolvedora Front-end",
        descricao: "Especialista em interfaces mágicas e experiências encantadoras.",
        imagem: "img/ariel.webp",
        linkedin: "https://www.linkedin.com/in/ariel-cristina-a04815212/",
        github: "https://github.com/ArielCSousa"
      },
      1: {
        nome: "Eduarda",
        cargo: "Desenvolvedora Back-end",
        descricao: "Cria fluxos encantadores com foco no usuário.",
        imagem: "img/eduarda.webp",
        linkedin: "https://linkedin.com/in/eduarda-dias-723a7820b/",
        github: "https://github.com/Diaseduarda01"
      },
      2: {
        nome: "Nicolas",
        cargo: "Engenheiro de Dados",
        descricao: "Mestre dos dados e estatísticas místicas.",
        imagem: "img/nicolas.webp",
        linkedin: "https://www.linkedin.com/in/nicolas-santos-663183342/",
        github: "https://github.com/NicolasNun"
      },
      3: {
        nome: "Rennan",
        cargo: "Desenvolvedor Full-stack",
        descricao: "Controla pipelines como um alquimista moderno.",
        imagem: "img/rennan.webp",
        linkedin: "https://www.linkedin.com/in/rennan-moura-5947771a3/",
        github: "https://github.com/RennanSMoura"
      },
      4: {
        nome: "Vitor",
        cargo: "Desenvolvedor Full-stack",
        descricao: "Conecta front e back com código sagrado.",
        imagem: "img/vitor.webp",
        linkedin: "https://www.linkedin.com/in/vitorsuave/",
        github: "https://github.com/VitorSuave"
      }
    };
  
    document.querySelectorAll('.btn-abrir-modal').forEach(botao => {
      botao.addEventListener('click', () => {
        const index = botao.dataset.index;
        const pessoa = equipe[index];
  
        if (pessoa) {
          modalImg.src = pessoa.imagem;
          modalName.textContent = pessoa.nome;
          modalRole.textContent = pessoa.cargo;
          modalDesc.textContent = pessoa.descricao;
          modalLinkedin.href = pessoa.linkedin;
          modalGithub.href = pessoa.github;
  
          // Exibe o modal e bloqueia o scroll
          modal.classList.remove("hidden");
          document.body.classList.add("modal-open");
        }
      });
    });
  
    closeModal.addEventListener("click", () => {
      modal.classList.add("hidden");
  
      // Libera o scroll quando o modal é fechado
      document.body.classList.remove("modal-open");
    });
  
    window.addEventListener("click", (e) => {
      if (e.target === modal) {
        modal.classList.add("hidden");
  
        // Libera o scroll quando o modal é fechado ao clicar fora dele
        document.body.classList.remove("modal-open");
      }
    });
  }
  
  // Ativar o modal após carregar o DOM
  document.addEventListener("DOMContentLoaded", modalEquipe);
  