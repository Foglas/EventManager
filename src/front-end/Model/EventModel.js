function getEventByFilterQuery(query){
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


function getEvent(){
   return fetch("http://localhost:8080/api/events")
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
    fetch("http://localhost:8080/api/auth/event/"+e.target.dataset.id+"/attend",headers)
    .then((response) => response.json())
    .then((data) => 
    {
        const button =  e.target;
        setSuccessButtonAttendStyle(button);
        console.log(data);
    })
    .catch((error) => console.log("There is problem", error));
    

}