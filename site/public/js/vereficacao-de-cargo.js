  window.onload = function () {
    const cargo = sessionStorage.getItem("CARGO");

    const cardUsuarios = document.getElementById("cardUsuarios");
    const cardPerfis = document.getElementById("cardPerfis");
    const cardNotificacoes = document.getElementById("cardNotificacoes");
    const cardSugestoes = document.getElementById("cardSugestoes");

    cardUsuarios.style.display = "none";
    cardPerfis.style.display = "none";
    cardNotificacoes.style.display = "none";
    cardSugestoes.style.display = "none";

    if (cargo === "admin") {
      cardUsuarios.style.display = "flex";
      cardPerfis.style.display = "flex";
      cardNotificacoes.style.display = "flex";
      cardSugestoes.style.display = "flex";
    } else if (cargo === "gerente") {
      cardPerfis.style.display = "block";
      cardSugestoes.style.display = "block";
    } else if (cargo === "atendente") {
      cardPerfis.style.display = "block";
    } else {
      alert("Cargo não identificado. Redirecionando para o login.");
      window.location.href = "login.html"; 
    }
  }
