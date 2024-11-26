document.addEventListener("DOMContentLoaded", function () {

    const queryParams = new URLSearchParams(window.location.search);
    const email = queryParams.get("email");

    const userInfo = document.getElementById("userInfo");
    userInfo.textContent = `Validating account with address: ${email}`;

    document.getElementById("otpForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const otp = document.getElementById("otp").value;

        fetch(`/api/auth/verify-otp?email=${encodeURIComponent(email)}&otp=${encodeURIComponent(otp)}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        })

            .then((response) => response.json())
            .then((data) => {
                if (data.success) {
                    Toastify({
                        text: "OTP validated successfully! You can now log in.",
                        duration: 3000,
                        close: true,
                        gravity: "top",
                        position: "right",
                        backgroundColor: "#4CAF50",
                    }).showToast();

                    setTimeout(() => {
                        window.location.href = "/api/auth/login";
                    }, 3000);
                } else {
                    alert(data.message || "OTP validation failed");
                }
            })
            .catch((error) => console.error("Error: ", error));
    })
})