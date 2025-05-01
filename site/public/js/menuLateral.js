function mostrarItenMenu() {
    const header = document.querySelector("header");
    header.classList.toggle("expandido");

    if (header.classList.contains("expandido")) {
      iconeMenu.src = "./img/excluir.png"; 
     } else {
      iconeMenu.src = "./img/icons8-hamburguer.svg"; 
    }
  }