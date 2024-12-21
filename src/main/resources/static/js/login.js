document.getElementById("loginForm").addEventListener("submit", async function (event) {
    event.preventDefault();
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;


    const response = await fetch("/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({username, password}),
    });


    console.log(response.status);

    const data = await response.json();

    console.log(data);


    localStorage.setItem('token', data.accessToken);
    localStorage.setItem('role', JSON.stringify(data.role));

    if (data.accessToken) {

        console.log(localStorage);

        Toastify({
            text: "Login successful!",
            duration: 3000,
            close: true,
            gravity: "top",
            position: "right",
            backgroundColor: "#4CAF50", // Success green
        }).showToast();


        if (data.role.includes('HR')) {
            window.location.href = "/api/hr/dashboard";
        } else if (data.role.includes('USER')) {
            console.log('Salam');
            await fetch("/api/user/dashboard", {
                headers: {
                    "Authorization": `Bearer ${data.accessToken}`
                }
            });
        }
    } else {
        alert(data.message || "Invalid Credentials");
    }
})