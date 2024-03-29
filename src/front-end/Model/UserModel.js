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

function register(user){
        const RegisterHeaders = {
            method: "POST",
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
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
            })
          }

          console.log("email  " + user.get("email"));
          console.log("username  " + user.get("username"));
          console.log("pass  " + user.get("pass"));
          console.log("pass2  " + user.get("pass2"));
          console.log("name  " + user.get("name"));
          console.log("surname  " + user.get("surname"));
          console.log("birth  " + user.get("birth"));
          console.log("phone  " + user.get("phone"));

        fetch("http://localhost:8080/api/user/register",RegisterHeaders)
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
        })
        .catch((error) => console.log("There is problem", error));
}

function getOrganizations(){
  const headers = {
      method: "GET",
      headers: {
        'Content-Type': 'application/json'
      }
    }
  
  return fetch("http://localhost:8080/api/user/" + localStorage.getItem("userId")+"/organization",headers)
  .then((response) => response.json())
  .then((data) => {
    console.log("OrganizationId " + data.id);
    return data;
  })
  .catch((error) => {
      console.log("error ", error);
      return null;
  });
}

function updateUser(user){
  token = 'Bearer ' + localStorage.getItem("token");
  const RegisterHeaders = {
    method: "POST",
    headers: {
      'Authorization': token,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        "email": user.get("email"),
        "username": user.get("username"),
        "name" : user.get("name"),
        "surname" : user.get("surname"),
        "dateOfBirth" : user.get("birth"),
        "phone" : user.get("phone")
        
    })
  }

  console.log("email  " + user.get("email"));
  console.log("username  " + user.get("username"));
  console.log("name  " + user.get("name"));
  console.log("surname  " + user.get("surname"));
  console.log("dateOfBirth  " + user.get("birth"));
  console.log("phone  " + user.get("phone"));

fetch("http://localhost:8080/api/auth/user/update",RegisterHeaders)
.then((response) => response.json())
.then((data) => {
    console.log(data);
    localStorage.setItem("token", data.message);
})
.catch((error) => console.log("There is problem", error));
}