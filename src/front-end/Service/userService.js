const loginLogoutA = document.querySelectorAll(".login_logout");
const onlyLoginA = document.querySelector(".onlyLogin");
const navbar = document.querySelector("#classicNav");
let isShowed = false;

//nastaví vzhled navbaru pro přihlášeného, či nepřihlášeného uživatele
function setBasicUserView(){
    console.log("login")
    if(isUserLogged()){
        loginLogoutA.forEach((login) =>{
            console.log("users")
            login.textContent = "Logout";
            login.setAttribute("href","");
            login.addEventListener("click", handleLogout);
        })
      
    } else{
        loginLogoutA.forEach((login)=>{
            login.textContent = "Login";
            login.setAttribute("href","Login.html");
        })
        console.log("removed");
        navbar.removeChild(onlyLoginA);
    }
}

//nastaví vzhled navbaru pro přihlášeného, či nepřihlášeného uživatele pro mobilní verzi
function setBasicMobileUserView(){
    const isLogged = isUserLogged();

    if (!isShowed && isLogged){
    console.log("Hello");
    isShowed = true;
    setBasicUserView();
    innerNavLogin.classList.remove("none");
    innerNavLogin.classList.add("open");
    } else if (!isShowed && !isLogged){
    isShowed = true;
    innerNavNotLogin.classList.remove("none");
    innerNavNotLogin.classList.add("open");
    } else if (isShowed && isLogged){
    isShowed = false;
    innerNavLogin.classList.remove("open");
    innerNavLogin.classList.add("none");
    } else if (isShowed && !isLogged){
        isShowed = false;
        innerNavNotLogin.classList.remove("open");
        innerNavNotLogin.classList.add("none");
    }
}

//obsluha tlačítka na logout
function handleLogout(e){
    e.preventDefault();
    console.log("login");
    logout().then((response)=>{
        if(response){
        console.log("logout")
        window.location.assign("Events.html");
        localStorage.removeItem("token");
    }
    });
}


//vrací zda je uživatel přihlášen na základě tokenu
function isUserLogged(){
    if(!(localStorage.getItem("token") ==  null)){
        return true;
    } else{
        return false;
    }
}