document.addEventListener("DOMContentLoaded", function (){

    const queryParams = new URLSearchParams(window.location.search);
    const email = queryParams.get("email");

    const userInfo = document.getElementById("userInfo");
    userInfo.textContent = `Validating account with address: ${email}`;

    document.getElementById("otpForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const otp = document.getElementById("otp").value;

        fetch("api/auth/verify-otp", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({email, otp}),
        })

            .then((response) => response.json())
            .then((data) => {
                if(data.success){
                    alert("OTP validated successfully! You can now log in");
                    window.location.href = "/html/login.html";
                }else{
                    alert(data.message || "OTP validation failed");
                }
            })
            .catch((error) => console.error("Error: ", error));
    })
})