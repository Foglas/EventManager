const loginLogoutA = document.querySelector("#login_logout");
const onlyLoginA = document.querySelector(".onlyLogin");
const navbar = document.querySelector(".navbar");

function setBasicUserView(){
    console.log("login")
    if(isUserLogged()){
        loginLogoutA.textContent = "Logout";
        loginLogoutA.setAttribute("href","");
        loginLogoutA.addEventListener("click", handleLogout);
      
    } else{
        loginLogoutA.textContent = "Login";
        loginLogoutA.setAttribute("href","Login.html");
        navbar.removeChild(onlyLoginA);
        
    }
}

function handleLogout(e){
    e.preventDefault();
    console.log("login");
    logout().then(()=>{
        console.log("logout")
        window.location.assign("Events.html");
        localStorage.removeItem("token");

    });
}

function isUserLogged(){
    if(!(localStorage.getItem("token") ==  null)){
        return true;
    } else{
        return false;
    }
}