document.getElementById("signUpForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch("/api/auth/sign-up", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({username, email, password})
    })

        .then((response) => {
            if(!response.ok){
                throw new Error(`Error: ${response.statusText}`);
            }

            return response.json();
        })
        .then((data) => {
            console.log("Data: ", data)
            if (data.success) {
                Toastify({
                    text: "Sign-Up successful! Verify your account using the OTP sent to your email.",
                    duration: 3000,
                    close: true,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "#4CAF50",
                }).showToast();

                setTimeout(() =>{
                    window.location.href = `/api/auth/verify-otp?email=${encodeURIComponent(email)}`;
                }, 3000);
            } else {
                alert(data.message || "Sign Up failed!"); //modal
            }
        })
        .catch((error) => console.error("Error: ", error));

})