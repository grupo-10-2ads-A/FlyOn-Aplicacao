function menuShow() {
    let menuMobile = document.querySelector('.cabecalho_mobile-menu');
    if(menuMobile.classList.contains('open')){
        menuMobile.classList.remove('open');
    } else {
        menuMobile.classList.add('open');
    }
}