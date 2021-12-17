window.addEventListener('load', async () => {

    console.log('EXECUTED');

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        method: 'GET',
        credentials: 'include'
    });

    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'Employee') {
            window.location.href = 'Employee-Homepage.html';
        } else if (userObj.userRole === 'Finance Manager') {
            window.location.href = 'Finance-Manager-HP.html';
        }
    }

});

let loginButton = document.querySelector('#login-btn');

loginButton.addEventListener('click', loginButtonClicked);

function loginButtonClicked(e) {
    e.preventDefault();
    login();
}

async function login() {
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');

    try {
        let res = await fetch('http://localhost:8080/login', {
            method: 'POST',
            credentials: 'include', 
            body: JSON.stringify({ 
                username: usernameInput.value,
                password: passwordInput.value
            })
        });

        let data = await res.json();

        if (res.status === 400) {
            let loginErrorMessage = document.createElement('p');
            let loginDiv = document.querySelector('#login-info');

            loginErrorMessage.innerHTML = data.message;
            loginErrorMessage.style.color = 'white';
            loginDiv.appendChild(loginErrorMessage);
        }

        if (res.status === 200) {
            console.log(data.userRole);
            if (data.userRole === 'Employee') {
                
                window.location.href = 'Employee-Homepage.html';
            } else if (data.userRole === 'Finance Manager') {
                
                window.location.href = 'Finance-Manager-HP.html';
            }
        }

    } catch(err) {
        console.log(err);
    } 
}