const loginLogoutA = document.querySelector("#login_logout");

function setUserView(){
    if(isUserLogged()){
        loginLogoutA.textContent = "Logout";
        loginLogoutA.setAttribute("href","");
        loginLogoutA.addEventListener("click", handleLogout);
    } else{
        loginLogoutA.textContent = "Login";
        loginLogoutA.setAttribute("href","Login.html");
    }
}

function handleLogout(e){
   // e.preventDefault();
    console.log("login");
    logout().then(()=>{
        setUserView();
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