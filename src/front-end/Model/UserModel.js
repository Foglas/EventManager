function login(values){
  const headers = {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        "email": values.get("email"),
        "password": values.get("pass")
        }
    )
  }

  console.log("email  " + values.get("email"));
  console.log("pass  " + values.get("pass"));
 
return fetch("http://localhost:8080/api/user/login",headers)
.then((response) => response.json())
.then((data) => {
    console.log(data.message);
    return data.message;
})
.catch((error) => console.log("There is problem", error));
}

function logout(){
  let token;
  if(isUserLogged){
  token = 'Bearer ' + localStorage.getItem("token");
} else{
  return null;
}
  const headers = {
    method: "POST",
    headers: {
      'Authorization': token,
      'Content-Type': 'application/json'
    }
  }
 
return fetch("http://localhost:8080/api/auth/user/logout",headers)
.then((response) => response.json())
.then((data) => {
    console.log("user logout");
    console.log("user info" + data.message);
    return data.message;
})
.catch((error) => console.log("There is problem", error));
}

function getCurrentUser(token){
    console.log("click "+token);
    token = 'Bearer ' + token;
    const headers = {
        method: "GET",
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        }
      }
    
    return fetch("http://localhost:8080/api/auth/currentUser",headers)
    .then((response) => response.json())
    .then((data) => {
      console.log("current user data " + data.id);
      return data;
    })
    .catch((error) => {
        console.log("There is problem", error);
        return null;
    });
}

function register(values){
        const headers = {
            method: "POST",
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "email": values.get("email"),
                "username": values.get("username"),
                "password": values.get("pass"),
                "passwordAgain": values.get("pass2"),
                "userDetails" : {
                    "name" : values.get("name"),
                    "surname" : values.get("surname"),
                    "dateOfBirth" : values.get("birth"),
                    "phone" : values.get("phone")
                }
            })
          }

          console.log("email  " + values.get("email"));
          console.log("username  " + values.get("username"));
          console.log("pass  " + values.get("pass"));
          console.log("pass2  " + values.get("pass2"));
          console.log("name  " + values.get("name"));
          console.log("surname  " + values.get("surname"));
          console.log("birth  " + values.get("birth"));
          console.log("phone  " + values.get("phone"));

        fetch("http://localhost:8080/api/user/register",headers)
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
        })
        .catch((error) => console.log("There is problem", error));
}