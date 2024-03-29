function getEventsByFilterQuery(query){
    console.log(query);
    
   return fetch("http://localhost:8080/api/events/search?"+query)
    .then((response) => response.json())
    .then((data) => 
    {
        console.log(data);
        return data;
    })
    .catch((error) => console.log("There is problem", error));
}


function getEvents(){
   return fetch("http://localhost:8080/api/events")
    .then((response) => response.json())
    .then((data) => 
    {
        console.log(data);
       return data;
    })
    .catch((error) => console.log("There is problem", error));
}

function getEvent(id){
    return fetch("http://localhost:8080/api/events/"+id)
     .then((response) => response.json())
     .then((data) => 
     {
         console.log(data);
        return data;
     })
     .catch((error) => console.log("There is problem", error));
 }
 

function getAttendedEvents(){
    const token = 'Bearer ' + localStorage.getItem('token');
    console.log("click "+token);
    const headers = {
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        }
      }
    
    return fetch("http://localhost:8080/api/auth/user/"+localStorage.getItem("userId") +"/getAttendedEvents",headers)
    .then((response) => {
        return response.json()
    })
    .catch((error) => {
        console.log("There is problem", error);
        return null;
    });
}

function cancelAttendToEvent(e){
    e.preventDefault();
    const token = 'Bearer ' + localStorage.getItem('token');
    console.log("click "+token);
    const headers = {
        method: "DELETE",
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        },
      }

    
    return fetch("http://localhost:8080/api/auth/event/"+ e.target.dataset.id +"/cancelAttend",headers)
    .then((response) => {
        console.log("Attend canceled");
        const button =  e.target;
        setUnsuccessfulButtonAttendStyle(button)
    })
    .catch((error) => {
        console.log("There is problem", error);
        return null;
    });
}

function attendToEvent(e){
    e.preventDefault();
    const token = 'Bearer ' + localStorage.getItem('token');
    console.log("click "+token);
    const headers = {
        method: "POST",
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "state":"koupeno"
        })
      }

    console.log("event id in attendEvent " +e.target.dataset.id);
    fetch("http://localhost:8080/api/auth/event/"+ e.target.dataset.id +"/attend",headers)
    .then((response) => response.json())
    .then((data) => 
    {
        const button =  e.target;
        setSuccessButtonAttendStyle(button);
        console.log(data);
    })
    .catch((error) => console.log("There is problem", error));
}

function getMyEvents(id){
    const token = 'Bearer ' + localStorage.getItem('token');
    console.log("click "+token);
    const headers = {
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        }
      }
    
    return fetch("http://localhost:8080/api/organization/"+id+"/events", headers)
    .then((response) => {
        return response.json()
    })
    .catch((error) => {
        console.log("There is problem", error);
        return null;
    });
}

function deleteEvent(id){
    const token = 'Bearer ' + localStorage.getItem('token');
    console.log("click "+token);
    const headers = {
        method: "DELETE",
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        }
    }
    
    return fetch("http://localhost:8080/api/auth/event/"+id+"/delete", headers)
    .then((response) => {
        return response.json()
    })
    .catch((error) => {
        console.log("There is problem", error);
        return null;
    });
}

function saveEvent(event){
    const token = 'Bearer ' + localStorage.getItem('token');
    console.log("click "+token);
    const headers = {
        method: "POST",
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(event)
      }
      
    return fetch("http://localhost:8080/api/auth/event/organization/-1/save",headers)
    .then((response) => {
        console.log(response.status);
        checkResponseAndReturnOrThrow(response)
    }).catch((error)=>{
        throw new Error(error);
    })
    
}

function checkResponseAndReturnOrThrow(response){
    if(response.status != 200){
        response.json().then((error)=>{
            console.log("message " + error.message);
             throw new Error(error.message);
        });
     } else{
         return response.json();
     }
}