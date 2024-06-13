//controller který se stará o vykreslení přihlašovací/registrační stránky a její obsluhu

const registrationForm = document.querySelector("#registrationForm");
const loginForm = document.querySelector("#loginForm");
const formContainer = document.querySelector("#formContainer");
const registerButton = document.querySelector("#registerBtn");
const loginBtn = document.querySelector("#loginBtn");
const switchButton = document.querySelector("#switchRegLog");
const loginH = document.querySelector("#loginH");
const error = document.createElement("div");
const success = document.createElement("div");

window.addEventListener("DOMContentLoaded", initialize);

registerButton.addEventListener("click", handleRegister);
loginBtn.addEventListener("click", handleLogin);
switchButton.addEventListener("click", handleSwitchButton);
let formInUse;

//nastaví korektní místo divů pro success a error. Nastavuje defaultní formulář pro přihlášení.
function initialize(){
    formInUse = loginForm;
    console.log("console log")

    error.classList.add("errorDiv");
    success.classList.add("successDiv");
    formContainer.appendChild(error);
    formContainer.appendChild(success);
    console.log("loaded");
    initializeResponseToUser();

    formContainer.removeChild(registrationForm);
    formContainer.classList.remove("hidden");

}


//obsluha registrace.
function handleRegister(e){
    e.preventDefault();
    const values = getValuesFromForm(formInUse);
    register(values).then((response)=>{
        if(response){
            addSuccessMessage(response.message, 1, ()=>{
            window.location.assign("Login.html");
        });
    }
    })
}


//Vrátí hodnoty z formuláře
function getValuesFromForm(form){
    return formValues = new FormData(form);
}

//obsluha příhlášení
function handleLogin(e){
    e.preventDefault();
    const values = getValuesFromForm(formInUse);
    loginUser(values);
}


//obsluha tlačítka při kliknutí na změnu fomuláře. Změna stylu a změna aktuálního formuláře.
function handleSwitchButton(e){
    e.preventDefault();
    const textContent = switchButton.textContent;
    if(textContent == "Login"){
        switchButton.textContent = "Register";
        formContainer.removeChild(registrationForm);
        formContainer.appendChild(loginForm);
        loginH.textContent = "Přihlášení";
        removeErrror();
        formContainer
        formInUse = loginForm;
    } else {
        switchButton.textContent = "Login";
        formContainer.removeChild(loginForm);
        formContainer.appendChild(registrationForm);
        loginH.textContent = "Registrace";
        formInUse = registrationForm;
        removeErrror();
        console.log("register button");
    }

    console.log(error)
    formContainer.removeChild(switchButton);
    formContainer.appendChild(switchButton);
    formContainer.removeChild(error);
    formContainer.removeChild(success);
    formContainer.appendChild(error);
    formContainer.appendChild(success);   

}


/**Stará se o přihlášení uživatele do aplikace.
 *  Po vrácení tokenu z be uloží do localstorage a musí volat získání info o aktuálním uživateli z důvodu zjištění id
 * a uložení do localstorage. Po úspěšném přihlášení přesměruje stránku na přehled eventů.
 */
function loginUser(values){
    login(values).then((response) => {
        if(response){
        console.log("hel")
        const token = response.message;
        localStorage.setItem("token", token);
        console.log("token " + token);
        getCurrentUser(token).then((currentUser) => {
            localStorage.setItem("userId", currentUser.id);
            localStorage.setItem("token", token);
            window.location.assign("Events.html");
        });
    }
  });

}
