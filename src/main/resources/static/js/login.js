document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault();
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({username, password}),
    })

        .then((response) => response.json())
        .then((data) => {

            console.log("Data: ", data);

            localStorage.setItem('token', data.accessToken);
            localStorage.setItem('roles', JSON.stringify(data.roles));
            if (data.accessToken) {
                Toastify({
                    text: "Login successful!",
                    duration: 3000,
                    close: true,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "#4CAF50", // Success green
                }).showToast();

                const roles = data.roles;

                setTimeout(() => {
                    if(roles.includes('HR')){
                        console.log('Salam');
                        window.location.href = "/api/hr/dashboard";
                    } else if(roles.includes('USER')){
                        window.location.href = "/api/user/dashboard";
                    }
                }, 3000);
            } else {
                alert(data.message || "Invalid Credentials");
            }
        })
        .catch((error) => console.error("Error: ", error));
})