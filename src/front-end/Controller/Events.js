const eventContainer = document.querySelector(".eventContainer");
const eventNav = document.querySelector("#event_nav");
const searchBar = document.querySelector("#searchBar");
const buttonFind = document.querySelector(".submit_findEvent");


console.log(eventContainer);

window.addEventListener("DOMContentLoaded", initialize)

searchBar.addEventListener("submit", getFilterValuesFromForm);


function initialize(){
  removeAllEvents();
  
//uložen token a id usera, než bude vytvořeno přihlašování
//localStorage.setItem("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJldmUzM25ALmN6IiwiZXhwIjoxNzEwMjUyOTM5fQ.zTiJsLuxOrUyQPu9Tp9Zx_AcWCVVwC4j0hJIz1siN_O6JTTlq-ft5A4HruS4RagnyXdOartKrpMS1tetSnzHKg");
//localStorage.setItem("userId",9);
 
  setBasicUserView();

  if(!isUserLogged()){
    eventNav.classList.add("hidden");
    
  }

  if(localStorage.getItem("category") === null){
      console.log("category loading");
      getCategories();
  } else{
      console.log("category is loaded");
  }
  
  if(localStorage.getItem("place") === null){
      console.log("place loading");
      getPlaces();
  } else{
      console.log("place is loaded");
  }
getEvents().then((events)=>{
    setEvents(events);
});
    
fillSearchBarHtml();
}


/*
function addEventListnerToAll(buttons){
    console.log("addEvenetListener " +buttons.length);
   buttons.forEach((button)=>{
        button.addEventListener("click", attendToEvent);
    });
}
*/

/*
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
*/


/*function setSuccessButtonAttendStyle(button){
    console.log("success style change");
    button.textContent = "Přihlášeno";
    button.classList.remove("buttonAttend");
    button.classList.add("buttonAttendSuccess");
}
*/
/*
function getEventByFilterQuery(query){
console.log(query);

fetch("http://localhost:8080/api/events/search?"+query)
.then((response) => response.json())
.then((data) => 
{
    console.log(data);
    removeAllEvents();
    setEvents(data);
})
.catch((error) => console.log("There is problem", error));
}
*/

function getFilterValuesFromForm(e){
    e.preventDefault();
    console.log("getFormValues")
    let formValues = new FormData(searchBar);
    let query = madeEventQuery(formValues);
    getEventsByFilterQuery(query).then((events) => {
        setEvents(events)
    });
    removeAllEvents();
}

function madeEventQuery(formValues){
  let query = "";
  let categoryNotice = false;
  for (const [key, value] of formValues) {
    if(!categoryNotice){
        if(key === "categories"){
            categoryNotice = true;
            query += key+"="+value;
            
        } else if(value !== "" && value !== "disabled"){
        query += key+"="+value+"&";
    }
    } else {
        query +=","+value;
    }
  }
  console.log("query " + query);
  return query;
  }



/*
function getPlace(){
    fetch("http://localhost:8080/api/places")
    .then((response) => response.json())
    .then((data) => 
    {
        console.log(data);
        let index = 0;
        data.forEach(element => {
            localStorage.setItem("place"+index, JSON.stringify(element));  
            index +=1;
        });
        localStorage.setItem("PlaceCount", index);
    })
    .catch((error) => console.log("There is problem", error));
    
}
*/

/*
function getCategory(){
    fetch("http://localhost:8080/api/category")
    .then((response) => response.json())
    .then((data) => 
    {
        console.log(data);
        let index = 0;
        data.forEach(element => {
            localStorage.setItem("category"+index, JSON.stringify(element));  
            index +=1;    
        });
        localStorage.setItem("CategoryCount", index);

    })
    .catch((error) => console.log("There is problem", error));
}
*/


function fillSearchBarHtml(){
console.log(localStorage.getItem("category"));

addPlacesToElement(searchBar);

const timeBar =  document.createElement("div");
timeBar.classList.add("time_bar");

const timeLabel = document.createElement("label");
timeLabel.setAttribute("for","time");
timeLabel.textContent = "Čas začátku: ";

const inputTime = document.createElement("input");
inputTime.setAttribute("type","datetime-local");
inputTime.setAttribute("name", "time");
inputTime.setAttribute("id","time");

timeBar.appendChild(timeLabel);
timeBar.appendChild(inputTime);

searchBar.appendChild(timeBar);

const categoryBar = document.createElement("div")

addCategoriesToElement(categoryBar);

searchBar.appendChild(categoryBar);
}

/*
function getEvent(){
    fetch("http://localhost:8080/api/events")
    .then((response) => response.json())
    .then((data) => 
    {
        console.log(data);
        setEvents(data);
    })
    .catch((error) => console.log("There is problem", error));

}
*/
/*
function setEvents(data){
    if(data.length === 0){
        console.log("Eventy nebyli nalezeny.")
        displayZeroEvents();
    } else {
        addEventsToHtml(data);
        console.log(data.length);
    }   
}
*/

/*
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
    .then((response) => {return response.json()})
    .catch((error) => {
        console.log("There is problem", error);
        return null;
    });
}
*/

/*
function setAttendedEventsStyle(){
    getAttendedEvents().then((events) => {
        events.forEach((event) => {
            buttonAttend.forEach((button)=>{
                const idEvent = button.dataset.id;
                console.log("comparison " + idEvent + "  " + event.id);
                if(idEvent == event.id){
                    setSuccessButtonAttendStyle(button);
                }
            });

        })
    })
}

*/

/*
function displayZeroEvents(){
        const newElement = document.createElement("div");
        newElement.innerHTML = `<h1 id="exception">Nebyly nalezeny žádné eventy</h1>`
        console.log(newElement.innerHTML)
        eventContainer.appendChild(newElement);
}
*/


