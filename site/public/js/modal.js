function ModalSaibaMaisFlyon(tipo) {
    const titulo = document.getElementById('modalTituloFlyon');
    const texto = document.getElementById('modalTextoFlyon');
    const modal = document.getElementById('modalUnico');
  
    const conteudos = {
      tecnologia: {
        titulo: "Tecnologia de Ponta",
        texto: "Nosso sistema conecta diretamente com a ANAC, trazendo dados seguros, confiáveis e em tempo real para sua análise e tomada de decisão."
      },
      dashboards: {
        titulo: "Dashboards Interativos",
        texto: "Você visualiza rotas, detecta padrões e toma decisões otimizadas em poucos cliques."
      },
      inteligencia: {
        titulo: "Análise Inteligente",
        texto: "Processamos dados brutos e extraímos informações valiosas que ampliam sua competitividade."
      },
      acesso: {
        titulo: "Acesso Total",
        texto: "Use de onde estiver, com segurança e alto desempenho."
      },
      geral: {
        titulo: "Soluções Inovadoras",
        texto: "Unimos dados, inteligência e visualização para transformar a forma como você atua no setor aéreo."
      }
    };
  
    const conteudo = conteudos[tipo] || conteudos['geral'];
  
    titulo.innerText = conteudo.titulo;
    texto.innerText = conteudo.texto;
  
    modal.classList.remove('hidden');
    modal.classList.add('show');
  
    // BLOQUEIA O SCROLL DA PÁGINA
    document.body.style.overflow = 'hidden';
  }
  
  function fecharModalFlyon() {
    const modal = document.getElementById('modalUnico');
    modal.classList.remove('show');
    modal.classList.add('hidden');
  
    // LIBERA O SCROLL DA PÁGINA
    document.body.style.overflow = 'auto';
  }
  