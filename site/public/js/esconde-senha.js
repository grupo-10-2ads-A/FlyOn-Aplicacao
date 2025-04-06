const pwShowHide = document.querySelectorAll(".showHidePw");

pwShowHide.forEach((eyeIcon) => {
    eyeIcon.addEventListener("click", () => {
        const input = eyeIcon.previousElementSibling;

        // Zoom ao clicar já está no CSS com :active
        if (input.type === "password") {
            input.type = "text";
            eyeIcon.classList.replace("uil-eye-slash", "uil-eye");

            // Animação opcional: "piscar"
            eyeIcon.classList.add("blink");
            setTimeout(() => eyeIcon.classList.remove("blink"), 150);
        } else {
            input.type = "password";
            eyeIcon.classList.replace("uil-eye", "uil-eye-slash");

            eyeIcon.classList.add("blink");
            setTimeout(() => eyeIcon.classList.remove("blink"), 150);
        }
    });
});
