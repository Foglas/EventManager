const registrationForm = document.querySelector("#registrationForm");
const loginForm = document.querySelector("#loginForm");
const formContainer = document.querySelector("#formContainer");
const registerButton = document.querySelector("#registerBtn");
const loginBtn = document.querySelector("#loginBtn");
const switchButton = document.querySelector("#switchRegLog");
const loginH = document.querySelector("#loginH");


window.addEventListener("DOMContentLoaded", initialize);
registerButton.addEventListener("click", handleRegister);
loginBtn.addEventListener("click", handleLogin);
switchButton.addEventListener("click", handleSwitchButton);
let formInUse;

function initialize(){
    formInUse = loginForm;
    console.log("console log")
  
    formContainer.removeChild(registrationForm);
    formContainer.classList.remove("hidden");
}

function handleRegister(e){
    e.preventDefault();
    const values = getValuesFromForm(formInUse);
    register(values);
}

function getValuesFromForm(form){
    return formValues = new FormData(form);
}

function handleLogin(e){
    e.preventDefault();
    const values = getValuesFromForm(formInUse);
    loginUser(values);
}


function handleSwitchButton(e){
    e.preventDefault();
    const textContent = switchButton.textContent;
    if(textContent == "Login"){
        switchButton.textContent = "Register";
        formContainer.removeChild(registrationForm);
        formContainer.appendChild(loginForm);
        loginH.textContent = "Přihlášení";
        formInUse = loginForm;
    } else {
        switchButton.textContent = "Login";
        formContainer.removeChild(loginForm);
        formContainer.appendChild(registrationForm);
        loginH.textContent = "Registrace";
        formInUse = registrationForm;
        console.log("register button");
    }
    formContainer.removeChild(switchButton);
    formContainer.appendChild(switchButton);   
}

function loginUser(values){
    login(values).then((token) => {
    console.log("token " + token);
    getCurrentUser(token).then((currentUser) => {
        localStorage.setItem("userId", currentUser.id);
        localStorage.setItem("token", token);
        window.location.assign("Events.html");
    });

  });
}
