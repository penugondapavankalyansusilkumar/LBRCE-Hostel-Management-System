document.addEventListener("DOMContentLoaded", function () {

    // Confirm before deleting or performing critical actions
    const confirmForms = document.querySelectorAll(
        "form[data-confirm]"
    );

    confirmForms.forEach(function (form) {
        form.addEventListener("submit", function (event) {
            const message =
                form.getAttribute("data-confirm") ||
                "Are you sure you want to continue?";

            if (!confirm(message)) {
                event.preventDefault();
            }
        });
    });

    // Basic validation for required form fields
    const forms = document.querySelectorAll("form");

    forms.forEach(function (form) {
        form.addEventListener("submit", function () {
            const requiredFields =
                form.querySelectorAll("[required]");

            requiredFields.forEach(function (field) {
                field.classList.remove("input-error");
            });
        });
    });

    // Automatically hide success messages after 5 seconds
    const successMessages =
        document.querySelectorAll(".alert-success");

    successMessages.forEach(function (message) {
        setTimeout(function () {
            message.style.transition = "opacity 0.5s";
            message.style.opacity = "0";

            setTimeout(function () {
                message.style.display = "none";
            }, 500);
        }, 5000);
    });

});