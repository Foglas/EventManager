
//volání api pro přihlášení uživatele.
function login(values){
  const body = {
    "email": values.get("email"),
    "password": values.get("pass")
    }

  return sendToApi("/api/user/login",()=>{},false ,body, "POST", true);
}

//volání api pro odhlášení uživatele
function logout(){
  return sendToApi("/api/auth/user/logout",()=>{}, true,"", "POST", false);
}

//volání api pro získání informací o přihlášeném uživateli
function getCurrentUser(){
  console.log("token from cu " + localStorage.getItem("token"));
  return sendToApi("/api/auth/currentUser",()=>{}, true,"", "GET", false);
}

//volání api pro registraci uživatele. User obsahuje všechny hodnoty pro vytvoření
function register(user){
  const body = {
    "email": user.get("email"),
    "username": user.get("username"),
    "password": user.get("pass"),
    "passwordAgain": user.get("pass2"),
    "userDetails" : {
        "name" : user.get("name"),
        "surname" : user.get("surname"),
        "dateOfBirth" : user.get("birth"),
        "phone" : user.get("phone")
    }
  }
  console.log(body);

  return sendToApi("/api/user/register",()=>{}, false, body, "POST", true);
}

//volání api pro získání všech organizací uživatele
function getOrganizations(){
  return sendToApi("/api/user/" + localStorage.getItem("userId")+"/organization",()=>{}, true, "", "GET", false);
}

//volání api pro update informací uživatele. User obsahuje data k aktualizaci
function updateUser(user){
const body = {
  "email": user.get("email"),
  "username": user.get("username"),
  "name" : user.get("name"),
  "surname" : user.get("surname"),
  "dateOfBirth" : user.get("birth"),
  "phone" : user.get("phone")
};

return sendToApi("/api/auth/user/update",()=>{}, true, body, "PUT", true);
}
