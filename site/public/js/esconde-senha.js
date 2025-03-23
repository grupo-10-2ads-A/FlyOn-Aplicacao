const pwShowHide = document.querySelectorAll(".showHidePw"), 
pwFields = document.querySelectorAll(".password input"); 

pwShowHide.forEach((eyeIcon, index) => {
eyeIcon.addEventListener("click", () => {
    const pwField = pwFields[index];

    if (pwField.type === "password") {
        pwField.type = "text"; 
        eyeIcon.classList.replace("uil-eye-slash", "uil-eye");
    } else {
        pwField.type = "password"; 
        eyeIcon.classList.replace("uil-eye", "uil-eye-slash");
    }
});
});