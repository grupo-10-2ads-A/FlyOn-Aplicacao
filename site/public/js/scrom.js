function menuShow() {
    const menuMobile = document.querySelector(".cabecalho_mobile-menu");
    menuMobile.classList.toggle("open");
  }
  
  // Scroll navbar color + troca de cor do menu mobile
  window.addEventListener("scroll", function () {
    const navbar = document.querySelector("header.navbar");
    const menuMobile = document.querySelector(".cabecalho_mobile-menu");
  
    if (window.scrollY > 50) {
      navbar.classList.add("scrolled");
    } else {
      navbar.classList.remove("scrolled");
    }
  
    // Muda a cor do menu mobile se passou de 300px (sem if condicional por classe)
    menuMobile.style.backgroundColor = window.scrollY > 300 ? "#10004f" : "#0986cb";
  });
  