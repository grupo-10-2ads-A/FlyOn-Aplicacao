function logout() {
  
    localStorage.removeItem('ID_USUARIO');
    localStorage.removeItem('NOME_USUARIO');
    localStorage.removeItem('EMAIL_USUARIO');
    window.location.href = '../auth/login.html';
}